package com.univ.tracedin.domain.span;

import java.util.List;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.ServiceNode;

public interface SpanRepository {

    void save(List<Span> spans);

    Span findById(String id);

    List<Span> findByProjectKeyAndSpanKind(String projectKey, SpanKind spanKind);

    SearchResult<Trace> findTracesByServiceNode(ServiceNode serviceNode, SearchCursor cursor);

    List<Span> findByTraceId(TraceId traceId);

    List<EndTimeBucket> getTraceHitMapByProjectKey(String projectKey);
}
