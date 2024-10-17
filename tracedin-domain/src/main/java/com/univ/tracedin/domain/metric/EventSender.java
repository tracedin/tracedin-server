package com.univ.tracedin.domain.metric;

public interface EventSender {

    void send(ServiceMetrics serviceMetrics);
}
