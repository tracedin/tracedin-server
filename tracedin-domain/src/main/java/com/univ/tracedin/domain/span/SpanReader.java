package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;

@Component
@RequiredArgsConstructor
public class SpanReader {

    private final SpanRepository spanRepository;

    public List<Span> read(String projectKey, SpanType spanType, SpanKind spanKind) {
        return spanRepository.findByProjectKeyAndSpanKind(projectKey, spanType, spanKind);
    }

    public SearchResult<Trace> read(TraceSearchCond cond, SearchCursor cursor) {
        return spanRepository.findTracesByNode(cond, cursor);
    }

    public List<Span> read(TraceId traceId) {
        return spanRepository.findByTraceId(traceId);
    }
}
