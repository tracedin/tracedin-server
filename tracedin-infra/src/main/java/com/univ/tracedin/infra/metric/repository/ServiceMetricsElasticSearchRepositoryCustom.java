package com.univ.tracedin.infra.metric.repository;

import java.util.List;

import com.univ.tracedin.domain.metric.HttpRequestCount;

public interface ServiceMetricsElasticSearchRepositoryCustom {

    List<HttpRequestCount> getHttpRequestCount(String projectKey, String serviceName);
}
