package com.univ.tracedin.infra.span.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.json.JsonObject;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanType;
import com.univ.tracedin.domain.span.Trace;
import com.univ.tracedin.domain.span.TraceId;
import com.univ.tracedin.infra.span.document.SpanDocument;
import com.univ.tracedin.infra.span.exception.ElasticSearchException;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.CompositeAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.CompositeAggregationSource;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.TopHitsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SpanElasticSearchRepositoryCustomImpl implements SpanElasticSearchRepositoryCustom {

    private final String INDEX_NAME = "span";
    private final String SERVICE_AGGREGATION_KEY = "service_nodes";

    private final ElasticsearchClient client;

    @Override
    public List<SpanDocument> search(String projectKey, SpanKind spanKind) {
        Query spanQuery = createSpanQuery(projectKey, spanKind);

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
    public SearchResult<Trace> findTracesByServiceNode(
            String projectKey, String serviceName, int size, Map<String, Object> afterKey) {
        return executeESQuery(
                () -> {
                    SearchResponse<Void> response =
                            client.search(
                                    s ->
                                            s.index(INDEX_NAME)
                                                    .size(0)
                                                    .query(
                                                            createServiceSpanQuery(
                                                                    projectKey, serviceName))
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
                                                                                                                                        "startEpochMillis",
                                                                                                                                        "endEpochMillis",
                                                                                                                                        "attributes.data.http.url")))))));
    }

    private static Query createServiceSpanQuery(String projectKey, String serviceName) {
        return QueryBuilders.bool(
                b ->
                        b.must(
                                List.of(
                                        QueryBuilders.term(
                                                t -> t.field("projectKey").value(projectKey)),
                                        QueryBuilders.term(
                                                t -> t.field("serviceName").value(serviceName)),
                                        QueryBuilders.term(t -> t.field("spanType").value("HTTP")),
                                        QueryBuilders.term(
                                                t ->
                                                        t.field("parentSpanId")
                                                                .value("0000000000000000")))));
    }

    private Aggregation createServiceNameAggregation() {
        return Aggregation.of(a -> a.terms(t -> t.field("serviceName").size(100)));
    }

    private Query createSpanQuery(String projectKey, SpanKind spanKind) {
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
                                                                        .value(
                                                                                SpanType.HTTP
                                                                                        .name())),
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
        String url = null;

        if (source.containsKey("attributes")) {
            JsonObject attributes = source.getJsonObject("attributes");

            if (attributes.containsKey("data")) {
                JsonObject data = attributes.getJsonObject("data");

                if (data.containsKey("http.url")) {
                    url = data.getString("http.url");
                }
            }
        }

        return Trace.builder()
                .id(TraceId.from(source.getString("traceId")))
                .endPoint(url)
                .endEpochMillis(source.getJsonNumber("endEpochMillis").longValue())
                .startEpochMillis(source.getJsonNumber("startEpochMillis").longValue())
                .build();
    }

    private <T> T executeESQuery(ESSupplier<T> request) {
        try {
            return request.get();
        } catch (IOException e) {
            log.error("Failed to search spans", e);
            throw ElasticSearchException.EXCEPTION;
        }
    }
}
