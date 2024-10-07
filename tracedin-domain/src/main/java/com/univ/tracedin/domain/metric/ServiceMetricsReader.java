package com.univ.tracedin.domain.metric;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.ServiceNode;

@Component
@RequiredArgsConstructor
public class ServiceMetricsReader {

    private final ServiceMetricsRepository serviceMetricsRepository;

    public List<HttpRequestCount> readHttpRequestCount(ServiceNode serviceNode) {
        return serviceMetricsRepository.getHttpRequestCount(serviceNode);
    }
}
