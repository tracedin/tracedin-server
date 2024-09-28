package com.univ.tracedin.infra.span.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.univ.tracedin.infra.span.document.SpanDocument;

public interface SpanElasticSearchRepository
        extends ElasticsearchRepository<SpanDocument, String> {}
