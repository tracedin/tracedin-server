package com.univ.tracedin.infra.metric.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.univ.tracedin.infra.metric.document.ServiceMetricsDocument;

public interface ServiceMetricsElasticSearchRepository
        extends ElasticsearchRepository<ServiceMetricsDocument, String>,
                ServiceMetricsElasticSearchRepositoryCustom {}
