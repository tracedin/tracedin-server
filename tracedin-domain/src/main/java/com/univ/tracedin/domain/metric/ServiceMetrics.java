package com.univ.tracedin.domain.metric;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceMetrics {

    private String projectKey;
    private String serviceName;
    List<Metric> metrics;
}
