package com.univ.tracedin.domain.metric;

import java.util.List;

import com.univ.tracedin.domain.project.ServiceNode;

public interface ServiceMetricsRepository {

    void save(ServiceMetrics metrics);

    List<HttpRequestCount> getHttpRequestCount(ServiceNode serviceNode);
}
