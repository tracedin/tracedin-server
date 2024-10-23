package com.univ.tracedin.infra.metric.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Slf4j
@RequiredArgsConstructor
public class ServiceMetricsElasticSearchRepositoryCustomImpl
        implements ServiceMetricsElasticSearchRepositoryCustom {

    private final String INDEX_NAME = "service-metrics";
    private final ElasticsearchClient client;
}
