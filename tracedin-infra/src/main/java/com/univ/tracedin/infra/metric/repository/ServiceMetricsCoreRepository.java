package com.univ.tracedin.infra.metric.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.metric.HttpRequestCount;
import com.univ.tracedin.domain.metric.ServiceMetrics;
import com.univ.tracedin.domain.metric.ServiceMetricsRepository;
import com.univ.tracedin.domain.project.ServiceNode;
import com.univ.tracedin.infra.metric.document.ServiceMetricsDocument;

@Repository
@Transactional
@RequiredArgsConstructor
public class ServiceMetricsCoreRepository implements ServiceMetricsRepository {

    private final ServiceMetricsElasticSearchRepository serviceMetricsElasticSearchRepository;

    public void save(ServiceMetrics metrics) {
        serviceMetricsElasticSearchRepository.save(ServiceMetricsDocument.from(metrics));
    }

    @Override
    public List<HttpRequestCount> getHttpRequestCount(ServiceNode serviceNode) {
        return serviceMetricsElasticSearchRepository.getHttpRequestCount(
                serviceNode.getProjectKey(), serviceNode.getName());
    }
}
