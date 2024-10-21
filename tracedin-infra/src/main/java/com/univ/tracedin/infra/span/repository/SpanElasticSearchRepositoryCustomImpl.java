package com.univ.tracedin.infra.span.repository;

import static com.univ.tracedin.infra.elasticsearch.ESUtils.executeESQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.json.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.ResponseTimeBucket;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanType;
import com.univ.tracedin.domain.span.Trace;
import com.univ.tracedin.domain.span.TraceId;
import com.univ.tracedin.domain.span.TraceSearchCond;
import com.univ.tracedin.infra.span.document.SpanDocument;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.CompositeAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.CompositeAggregationSource;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.TopHitsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import io.micrometer.common.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class SpanElasticSearchRepositoryCustomImpl implements SpanElasticSearchRepositoryCustom {

    private final String INDEX_NAME = "span";
    private final String SERVICE_AGGREGATION_KEY = "service_nodes";

    private final ElasticsearchClient client;

    @Override
    public List<SpanDocument> search(String projectKey, SpanType spanType, SpanKind spanKind) {
        Query spanQuery = createSpanQuery(projectKey, spanType, spanKind);

        return executeESQuery(
                () ->
                        client
                                .search(
                                        s -> s.index(INDEX_NAME).size(10000).query(spanQuery),
                                        SpanDocument.class)
                                .hits()
                                .hits()
                                .stream()
                                .map(Hit::source)
                                .toList());
    }

    @Override
    public List<String> findServiceNames(String projectKey) {
        return executeESQuery(
                () -> {
                    SearchResponse<Void> response =
                            client.search(
                                    s ->
                                            s.index(INDEX_NAME)
                                                    .size(0)
                                                    .query(
                                                            QueryBuilders.term(
                                                                    t ->
                                                                            t.field("projectKey")
                                                                                    .value(
                                                                                            projectKey)))
                                                    .aggregations(
                                                            "service_nodes",
                                                            createServiceNameAggregation()),
                                    Void.class);

                    return response
                            .aggregations()
                            .get(SERVICE_AGGREGATION_KEY)
                            .sterms()
                            .buckets()
                            .array()
                            .stream()
                            .map(StringTermsBucket::key)
                            .map(FieldValue::stringValue)
                            .toList();
                });
    }

    @Override
    public SearchResult<Trace> findTracesByNode(
            TraceSearchCond cond, int size, Map<String, Object> afterKey) {
        return executeESQuery(
                () -> {
                    SearchResponse<Void> response =
                            client.search(
                                    s ->
                                            s.index(INDEX_NAME)
                                                    .size(0)
                                                    .query(createServiceSpanQuery(cond))
                                                    .aggregations(
                                                            "by_trace",
                                                            createTraceAggregation(size, afterKey))
                                                    .aggregations(
                                                            "trace_count",
                                                            Aggregation.of(
                                                                    a ->
                                                                            a.cardinality(
                                                                                    c ->
                                                                                            c.field(
                                                                                                    "traceId")))),
                                    Void.class);

                    CompositeAggregate spansByTrace =
                            response.aggregations().get("by_trace").composite();
                    List<Trace> traces = extractTraces(spansByTrace);
                    long traceCount =
                            response.aggregations().get("trace_count").cardinality().value();
                    Map<String, Object> nextAfterKey = toObjectMap(spansByTrace.afterKey());
                    return SearchResult.success(traces, nextAfterKey, traceCount);
                });
    }

    @Override
    public List<SpanDocument> findByTraceId(String traceId) {
        Query query = QueryBuilders.term(t -> t.field("traceId").value(traceId));

        return executeESQuery(
                () -> {
                    SearchResponse<SpanDocument> response =
                            client.search(
                                    s -> s.index(INDEX_NAME).size(100).query(query),
                                    SpanDocument.class);

                    return response.hits().hits().stream().map(Hit::source).toList();
                });
    }

    @Override
    public List<EndTimeBucket> getTraceHitMapByProjectKey(String projectKey, String serviceName) {
        Query query = rootSpanQuery(projectKey, serviceName);
        Aggregation aggregation = histogramAggregation();

        return executeESQuery(
                () -> {
                    SearchResponse<Void> response =
                            client.search(
                                    s ->
                                            s.index(INDEX_NAME)
                                                    .size(0)
                                                    .query(query)
                                                    .aggregations("by_end_time", aggregation),
                                    Void.class);

                    return parseHistogramResponse(response);
                });
    }

    private List<EndTimeBucket> parseHistogramResponse(SearchResponse<Void> response) {
        List<EndTimeBucket> endTimeBuckets = new ArrayList<>();

        Aggregate byEndTimeAgg = response.aggregations().get("by_end_time");
        DateHistogramAggregate dateHistogram = byEndTimeAgg.dateHistogram();

        for (DateHistogramBucket dateBucket : dateHistogram.buckets().array()) {
            long endEpochMillis = dateBucket.key();

            Map<String, Aggregate> attributesNested =
                    dateBucket.aggregations().get("attributes_nested").nested().aggregations();

            HistogramAggregate histogram = attributesNested.get("by_response_time").histogram();

            List<ResponseTimeBucket> responseTimeBuckets = new ArrayList<>();

            for (HistogramBucket histogramBucket : histogram.buckets().array()) {
                double responseTime = histogramBucket.key();
                long count = histogramBucket.docCount();
                ResponseTimeBucket responseTimeBucket =
                        ResponseTimeBucket.from(responseTime, count);
                responseTimeBuckets.add(responseTimeBucket);
            }
            long errorCount = attributesNested.get("error_status_count").filter().docCount();
            EndTimeBucket endTimeBucket =
                    EndTimeBucket.from(endEpochMillis, errorCount, responseTimeBuckets);
            endTimeBuckets.add(endTimeBucket);
        }

        return endTimeBuckets;
    }

    private Aggregation histogramAggregation() {
        Aggregation byResponseTimeAgg =
                Aggregation.of(
                        a ->
                                a.histogram(
                                        h ->
                                                h.field("attributes.data.http.response_time_ms")
                                                        .interval(200.0)
                                                        .minDocCount(1)));

        Aggregation errorStatusCountAgg =
                Aggregation.of(
                        a ->
                                a.filter(
                                        f ->
                                                f.range(
                                                        r ->
                                                                r.field(
                                                                                "attributes.data.http.status_code")
                                                                        .gte(JsonData.of(400)))));

        Aggregation attributesNestedAgg =
                Aggregation.of(
                        a ->
                                a.nested(n -> n.path("attributes"))
                                        .aggregations(
                                                Map.of(
                                                        "by_response_time",
                                                        byResponseTimeAgg,
                                                        "error_status_count",
                                                        errorStatusCountAgg)));

        return Aggregation.of(
                a ->
                        a.dateHistogram(
                                        dh ->
                                                dh.field("endEpochMillis")
                                                        .fixedInterval(Time.of(t -> t.time("5m")))
                                                        .minDocCount(1))
                                .aggregations(Map.of("attributes_nested", attributesNestedAgg)));
    }

    private Query rootSpanQuery(String projectKey, String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            return QueryBuilders.bool(
                    b ->
                            b.must(
                                    QueryBuilders.term(
                                            t -> t.field("projectKey").value(projectKey)),
                                    QueryBuilders.term(t -> t.field("spanType").value("HTTP")),
                                    QueryBuilders.term(
                                            t ->
                                                    t.field("parentSpanId")
                                                            .value("0000000000000000"))));
        }

        return QueryBuilders.bool(
                b ->
                        b.must(
                                QueryBuilders.term(t -> t.field("projectKey").value(projectKey)),
                                QueryBuilders.term(t -> t.field("serviceName").value(serviceName)),
                                QueryBuilders.term(t -> t.field("spanType").value("HTTP"))));
    }

    private List<Trace> extractTraces(CompositeAggregate spansByTrace) {
        List<Trace> traces = new ArrayList<>();
        spansByTrace
                .buckets()
                .array()
                .forEach(
                        bucket -> {
                            TopHitsAggregate spanDetails =
                                    bucket.aggregations().get("span_details").topHits();
                            spanDetails
                                    .hits()
                                    .hits()
                                    .forEach(
                                            hit -> {
                                                if (hit.source() == null) {
                                                    return;
                                                }
                                                Trace trace =
                                                        mapToTrace(
                                                                hit.source()
                                                                        .toJson()
                                                                        .asJsonObject());

                                                traces.add(trace);
                                            });
                        });
        return traces;
    }

    private Aggregation createTraceAggregation(int size, Map<String, Object> afterKey) {
        return Aggregation.of(
                a ->
                        a.composite(
                                        c -> {
                                            c.size(size)
                                                    .sources(
                                                            List.of(
                                                                    Map.of(
                                                                            "startEpochMillis",
                                                                            CompositeAggregationSource
                                                                                    .of(
                                                                                            ca ->
                                                                                                    ca
                                                                                                            .terms(
                                                                                                                    t ->
                                                                                                                            t.field(
                                                                                                                                            "startEpochMillis")
                                                                                                                                    .order(
                                                                                                                                            SortOrder
                                                                                                                                                    .Desc)))),
                                                                    Map.of(
                                                                            "traceId",
                                                                            CompositeAggregationSource
                                                                                    .of(
                                                                                            ca ->
                                                                                                    ca
                                                                                                            .terms(
                                                                                                                    t ->
                                                                                                                            t
                                                                                                                                    .field(
                                                                                                                                            "traceId"))))));
                                            if (afterKey != null && !afterKey.isEmpty()) {
                                                c.after(toFieldValueMap(afterKey));
                                            }
                                            return c;
                                        })
                                .aggregations(
                                        "span_details",
                                        Aggregation.of(
                                                agg ->
                                                        agg.topHits(
                                                                th ->
                                                                        th.size(1)
                                                                                .source(
                                                                                        src ->
                                                                                                src
                                                                                                        .filter(
                                                                                                                f ->
                                                                                                                        f
                                                                                                                                .includes(
                                                                                                                                        "traceId",
                                                                                                                                        "serviceName",
                                                                                                                                        "startEpochMillis",
                                                                                                                                        "endEpochMillis",
                                                                                                                                        "attributes.data.http.url",
                                                                                                                                        "attributes.data.http.status_code",
                                                                                                                                        "attributes.data.anomaly")))))));
    }

    private Query createServiceSpanQuery(TraceSearchCond cond) {
        Builder bool = QueryBuilders.bool();

        bool.must(
                QueryBuilders.term(
                        t -> t.field("projectKey").value(cond.serviceNode().getProjectKey())));
        bool.must(
                QueryBuilders.term(
                        t -> t.field("serviceName").value(cond.serviceNode().getName())));
        bool.must(QueryBuilders.term(t -> t.field("spanType").value("HTTP")));
        bool.must(QueryBuilders.term(t -> t.field("kind").value("SERVER")));

        if (cond.hasTimeRange()) {
            bool.must(
                    QueryBuilders.range(
                            r ->
                                    r.field("startEpochMillis")
                                            .gte(JsonData.of(cond.getEpochMillisStartTime()))));
            bool.must(
                    QueryBuilders.range(
                            r ->
                                    r.field("endEpochMillis")
                                            .lte(JsonData.of(cond.getEpochMillisEndTime()))));
        }

        return bool.build()._toQuery();
    }

    private Aggregation createServiceNameAggregation() {
        return Aggregation.of(a -> a.terms(t -> t.field("serviceName").size(100)));
    }

    private Query createSpanQuery(String projectKey, SpanType spanType, SpanKind spanKind) {
        return BoolQuery.of(
                        b ->
                                b.must(
                                        List.of(
                                                QueryBuilders.term(
                                                        t ->
                                                                t.field("projectKey")
                                                                        .value(projectKey)),
                                                QueryBuilders.term(
                                                        t ->
                                                                t.field("spanType")
                                                                        .value(spanType.name())),
                                                QueryBuilders.term(
                                                        t -> t.field("kind").value(spanKind.name()))
                                                //                                                ,
                                                // QueryBuilders.range(r -> // 개발 진행 중에는 테스트를 위해 주석
                                                // 처리
                                                //
                                                //      r.field("startEpochMillis")
                                                //
                                                //              .gte(JsonData.of("now-60s")))

                                                )))
                ._toQuery();
    }

    private Map<String, FieldValue> toFieldValueMap(Map<String, Object> afterKey) {
        return afterKey.entrySet().stream()
                .map(
                        entry -> {
                            if (entry.getValue() instanceof Long) {
                                return Map.entry(
                                        entry.getKey(), FieldValue.of((Long) entry.getValue()));
                            } else if (entry.getValue() instanceof String) {
                                return Map.entry(
                                        entry.getKey(), FieldValue.of((String) entry.getValue()));
                            } else if (entry.getValue() instanceof Integer) {
                                return Map.entry(
                                        entry.getKey(), FieldValue.of((Integer) entry.getValue()));
                            } else if (entry.getValue() instanceof Boolean) {
                                return Map.entry(
                                        entry.getKey(), FieldValue.of((Boolean) entry.getValue()));
                            } else {
                                throw new IllegalArgumentException(
                                        "Unsupported type: " + entry.getValue().getClass());
                            }
                        })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Object> toObjectMap(Map<String, FieldValue> afterKey) {
        return afterKey.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()._get()));
    }

    private Trace mapToTrace(JsonObject source) {
        JsonObject data = source.getJsonObject("attributes").getJsonObject("data");

        return Trace.builder()
                .id(TraceId.from(source.getString("traceId")))
                .isAnomaly(data.containsKey("anomaly"))
                .endPoint(data.getString("http.url"))
                .serviceName(source.getString("serviceName"))
                .statusCode(data.getInt("http.status_code"))
                .endEpochMillis(source.getJsonNumber("endEpochMillis").longValue())
                .startEpochMillis(source.getJsonNumber("startEpochMillis").longValue())
                .build();
    }
}
