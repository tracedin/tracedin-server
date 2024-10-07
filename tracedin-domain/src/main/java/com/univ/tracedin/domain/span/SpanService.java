package com.univ.tracedin.domain.span;

import java.util.List;

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

    public void appendSpan(List<Span> spans) {
        spanAppender.append(spans);
    }

    public SearchResult<Trace> getTraces(ServiceNode serviceNode, SearchCursor cursor) {
        return spanReader.read(serviceNode, cursor);
    }

    public SpanTree getSpanTree(TraceId traceId) {
        List<Span> spans = spanReader.read(traceId);
        return SpanTreeBuilder.build(spans);
    }
}
