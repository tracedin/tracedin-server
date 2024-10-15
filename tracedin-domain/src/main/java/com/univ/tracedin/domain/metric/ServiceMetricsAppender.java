package com.univ.tracedin.domain.metric;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceMetricsAppender {

    private final ServiceMetricsRepository serviceMetricsRepository;

    public void appendAll(List<ServiceMetrics> metrics) {
        serviceMetricsRepository.saveAll(metrics);
    }
}
