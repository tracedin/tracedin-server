package com.univ.tracedin.infra.span.repository;

import java.util.List;
import java.util.Map;

import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.Trace;
import com.univ.tracedin.infra.span.document.SpanDocument;

public interface SpanElasticSearchRepositoryCustom {

    List<SpanDocument> search(String projectKey, SpanKind spanKind);

    List<String> findServiceNames(String projectKey);

    SearchResult<Trace> findTracesByServiceNode(
            String projectKey, String serviceName, int size, Map<String, Object> afterKey);

    List<SpanDocument> findByTraceId(String traceId);
}
