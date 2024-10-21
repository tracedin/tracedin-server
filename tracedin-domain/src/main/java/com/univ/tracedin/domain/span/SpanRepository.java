package com.univ.tracedin.domain.span;

import java.util.List;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.ProjectKey;

public interface SpanRepository {

    void saveAll(List<Span> spans);

    Span findById(SpanId id);

    List<Span> findByProjectKeyAndSpanKind(
            ProjectKey projectKey, SpanType spanType, SpanKind spanKind);

    SearchResult<Trace> findTracesByNode(TraceSearchCond cond, SearchCursor cursor);

    List<Span> findByTraceId(TraceId traceId);

    List<EndTimeBucket> getTraceHitMapByProjectKey(ProjectKey projectKey, String serviceName);

    void save(Span span);
}
