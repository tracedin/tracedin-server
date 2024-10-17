package com.univ.tracedin.domain.metric;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.Node;

@Service
@RequiredArgsConstructor
public class ServiceMetricsService {

    private final ServiceMetricsReader serviceMetricReader;
    private final ServiceMetricsMessagePublisher serviceMetricsMessagePublisher;

    public void appendMetrics(ServiceMetrics metrics) {
        serviceMetricsMessagePublisher.publish(ServiceMetricsCollectedEvent.from(metrics));
    }

    public List<HttpRequestCount> getHttpRequestCount(Node node) {
        return serviceMetricReader.readHttpRequestCount(node);
    }
}
