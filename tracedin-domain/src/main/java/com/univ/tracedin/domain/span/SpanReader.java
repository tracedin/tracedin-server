package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.ServiceNode;

@Component
@RequiredArgsConstructor
public class SpanReader {

    private final SpanRepository spanRepository;

    public List<Span> read(String projectKey, SpanKind spanKind) {
        return spanRepository.findByProjectKeyAndSpanKind(projectKey, spanKind);
    }

    public SearchResult<Trace> read(ServiceNode serviceNode, SearchCursor cursor) {
        return spanRepository.findTracesByServiceNode(serviceNode, cursor);
    }

    public List<Span> read(TraceId traceId) {
        return spanRepository.findByTraceId(traceId);
    }
}
