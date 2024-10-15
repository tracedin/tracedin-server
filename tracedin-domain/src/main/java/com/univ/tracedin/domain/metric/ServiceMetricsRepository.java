package com.univ.tracedin.domain.metric;

import java.util.List;

import com.univ.tracedin.domain.project.Node;

public interface ServiceMetricsRepository {

    List<HttpRequestCount> getHttpRequestCount(Node node);

    void saveAll(List<ServiceMetrics> metrics);
}
