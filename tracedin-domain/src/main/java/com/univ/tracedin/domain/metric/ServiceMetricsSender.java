package com.univ.tracedin.domain.metric;

public interface ServiceMetricsSender {

    void send(ServiceMetrics serviceMetrics);
}
