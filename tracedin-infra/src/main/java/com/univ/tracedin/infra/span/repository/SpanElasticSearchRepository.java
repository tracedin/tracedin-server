package com.univ.tracedin.infra.span.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.univ.tracedin.infra.span.document.SpanDocument;

public interface SpanElasticSearchRepository
        extends ElasticsearchRepository<SpanDocument, String>, SpanElasticSearchRepositoryCustom {

    List<SpanDocument> findByProjectKeyAndServiceName(String projectKey, String serviceName);
}
