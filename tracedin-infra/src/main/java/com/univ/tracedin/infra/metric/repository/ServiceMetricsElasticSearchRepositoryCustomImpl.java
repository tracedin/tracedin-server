package com.univ.tracedin.infra.metric.repository;

import static com.univ.tracedin.infra.elasticsearch.ESUtils.executeESQuery;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.metric.HttpRequestCount;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Slf4j
@RequiredArgsConstructor
public class ServiceMetricsElasticSearchRepositoryCustomImpl
        implements ServiceMetricsElasticSearchRepositoryCustom {

    private final String INDEX_NAME = "service-metrics";
    private final ElasticsearchClient client;

    @Override
    public List<HttpRequestCount> getHttpRequestCount(String projectKey, String serviceName) {
        Query serviceHttpRequestCountQuery = createHttpRequestCountQuery(projectKey, serviceName);
        Aggregation timestampAggregationHistogram = createTimestampAggregationHistogram();

        return executeESQuery(
                () -> {
                    SearchResponse<Void> response =
                            client.search(
                                    s ->
                                            s.index(INDEX_NAME)
                                                    .size(10000)
                                                    .query(serviceHttpRequestCountQuery)
                                                    .aggregations(
                                                            "metrics_nested",
                                                            timestampAggregationHistogram),
                                    Void.class);
                    return response
                            .aggregations()
                            .get("metrics_nested")
                            .nested()
                            .aggregations()
                            .get("filtered_http_requests")
                            .filter()
                            .aggregations()
                            .get("by_timestamp")
                            .dateHistogram()
                            .buckets()
                            .array()
                            .stream()
                            .map(
                                    bucket -> {
                                        long timestamp = bucket.key();
                                        double value =
                                                bucket.aggregations()
                                                        .get("request_count_sum")
                                                        .sum()
                                                        .value();
                                        return HttpRequestCount.of(timestamp, (long) value);
                                    })
                            .toList();
                });
    }

    private Query createHttpRequestCountQuery(String projectKey, String serviceName) {
        return QueryBuilders.bool(
                b ->
                        b.must(
                                List.of(
                                        // projectKey와 serviceName은 일반 필드이므로 nested 쿼리 바깥에 위치
                                        QueryBuilders.term(
                                                t -> t.field("projectKey").value(projectKey)),
                                        QueryBuilders.term(
                                                t -> t.field("serviceName").value(serviceName)),
                                        // metrics는 nested 필드이므로 nested 쿼리 안에서 처리
                                        QueryBuilders.nested(
                                                n ->
                                                        n.path("metrics")
                                                                .query(
                                                                        q ->
                                                                                q.bool(
                                                                                        bq ->
                                                                                                bq
                                                                                                        .must(
                                                                                                                List
                                                                                                                        .of(
                                                                                                                                QueryBuilders
                                                                                                                                        .term(
                                                                                                                                                t ->
                                                                                                                                                        t.field(
                                                                                                                                                                        "metrics.name")
                                                                                                                                                                .value(
                                                                                                                                                                        "http.request.count"))
                                                                                                                                //                                                                                                                                , QueryBuilders
                                                                                                                                //                                                                                                                                        .range(
                                                                                                                                //                                                                                                                                                r ->
                                                                                                                                //                                                                                                                                                        r.field(
                                                                                                                                //                                                                                                                                                                        "metrics.timestamp")
                                                                                                                                //                                                                                                                                                                .gte(
                                                                                                                                //                                                                                                                                                                        JsonData
                                                                                                                                //                                                                                                                                                                                .of(
                                                                                                                                //                                                                                                                                                                                        "now-5h/h")))
                                                                                                                                ))))))));
    }

    private Aggregation createTimestampAggregationHistogram() {

        Aggregation requestCountSum = Aggregation.of(a -> a.sum(s -> s.field("metrics.value")));

        Aggregation byTimeStamp =
                Aggregation.of(
                        a ->
                                a.dateHistogram(
                                                dh ->
                                                        dh.field("metrics.timestamp")
                                                                .fixedInterval(fi -> fi.time("10m"))
                                                                .minDocCount(1))
                                        .aggregations(
                                                Map.of("request_count_sum", requestCountSum)));

        Aggregation httpRequestMetric =
                Aggregation.of(
                        a ->
                                a.filter(
                                                f ->
                                                        f.term(
                                                                t ->
                                                                        t.field("metrics.name")
                                                                                .value(
                                                                                        "http.request.count")))
                                        .aggregations(Map.of("by_timestamp", byTimeStamp)));

        return Aggregation.of(
                a ->
                        a.nested(n -> n.path("metrics"))
                                .aggregations(Map.of("filtered_http_requests", httpRequestMetric)));
    }
}
