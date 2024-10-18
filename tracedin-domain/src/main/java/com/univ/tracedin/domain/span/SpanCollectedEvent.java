package com.univ.tracedin.domain.span;

import java.io.Serializable;
import java.util.List;

public record SpanCollectedEvent(List<Span> spans) implements Serializable {

    public static SpanCollectedEvent from(List<Span> spans) {
        return new SpanCollectedEvent(spans);
    }

    public TraceId getKey() {
        return spans.stream().map(Span::getTraceId).findFirst().orElseThrow();
    }
}
