package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;

@Service
@RequiredArgsConstructor
public class SpanService {

    private final SpanReader spanReader;
    private final ConditionValidator conditionValidator;
    private final SpanCollectedMessagePublisher spanCollectedMessagePublisher;

    public void publishSpans(List<Span> spans) {
        spanCollectedMessagePublisher.publish(SpanCollectedEvent.from(spans));
    }

    public SearchResult<Trace> getTraces(TraceSearchCond cond, SearchCursor cursor) {
        conditionValidator.validate(cond);
        return spanReader.read(cond, cursor);
    }

    public SpanTree getSpanTree(TraceId traceId) {
        List<Span> spans = spanReader.read(traceId);
        return SpanTreeBuilder.build(spans);
    }
}
