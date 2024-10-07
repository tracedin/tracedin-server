package com.univ.tracedin.domain.metric;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceMetricsAppender {

    private final ServiceMetricsRepository serviceMetricsRepository;

    public void append(ServiceMetrics metrics) {
        serviceMetricsRepository.save(metrics);
    }
}
