package com.univ.tracedin.domain.metric;

public interface ServiceMetricsCollectedMessagePublisher {

    void publish(ServiceMetricsCollectedEvent serviceMetricsCollectedEvent);
}
