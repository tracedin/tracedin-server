package com.univ.tracedin.domain.span;

public interface SpanCollectedMessagePublisher {

    void publish(SpanCollectedEvent event);
}
