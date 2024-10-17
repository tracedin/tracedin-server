package com.univ.tracedin.domain.span;

public interface SpanMessagePublisher {

    void publish(SpanCollectedEvent event);
}
