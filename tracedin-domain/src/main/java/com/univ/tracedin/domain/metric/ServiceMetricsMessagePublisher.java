package com.univ.tracedin.domain.metric;

public interface ServiceMetricsMessagePublisher {

    void publish(ServiceMetricsCollectedEvent serviceMetricsCollectedEvent);
}
