package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.ServiceNode;

@Service
@RequiredArgsConstructor
public class SpanService {

    private final SpanAppender spanAppender;
    private final SpanReader spanReader;

    public void appendSpan(Span span) {
        spanAppender.append(span);
    }

    public SearchResult<Trace> getTraces(ServiceNode serviceNode, SearchCursor cursor) {
        return spanReader.read(serviceNode, cursor);
    }
}
