package com.univ.tracedin.domain.metric;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceMetricsReader {

    private final ServiceMetricsRepository serviceMetricsRepository;
}
