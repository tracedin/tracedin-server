package com.univ.tracedin.domain.metric;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.ServiceNode;

@Service
@RequiredArgsConstructor
public class ServiceMetricsService {

    private final ServiceMetricsAppender serviceMetricAppender;
    private final ServiceMetricsReader serviceMetricReader;

    public void appendMetrics(ServiceMetrics metrics) {
        serviceMetricAppender.append(metrics);
    }

    public List<HttpRequestCount> getHttpRequestCount(ServiceNode serviceNode) {
        return serviceMetricReader.readHttpRequestCount(serviceNode);
    }
}
