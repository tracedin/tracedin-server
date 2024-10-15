package com.univ.tracedin.domain.span;

import java.util.List;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.EndTimeBucket;

public interface SpanRepository {

    void saveAll(List<Span> spans);

    Span findById(String id);

    List<Span> findByProjectKeyAndSpanKind(String projectKey, SpanType spanType, SpanKind spanKind);

    SearchResult<Trace> findTracesByNode(TraceSearchCond cond, SearchCursor cursor);

    List<Span> findByTraceId(TraceId traceId);

    List<EndTimeBucket> getTraceHitMapByProjectKey(String projectKey, String serviceName);
}
