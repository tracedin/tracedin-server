package com.univ.tracedin.api.metric.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.univ.tracedin.domain.metric.Metric;
import com.univ.tracedin.domain.metric.MetricType;
import com.univ.tracedin.domain.metric.ServiceMetrics;
import com.univ.tracedin.domain.project.ProjectKey;

public record AppendServiceMetricsRequest(
        String projectKey, String serviceName, List<MetricRequest> metrics) {

    public record MetricRequest(
            String name,
            String description,
            String unit,
            String type,
            Double value,
            Long count,
            Double sum,
            Double min,
            Double max,
            Map<String, Object> attributes) {

        public Metric toDomain() {
            return Metric.builder()
                    .name(name)
                    .description(description)
                    .unit(unit)
                    .type(MetricType.valueOf(type))
                    .value(value)
                    .count(count)
                    .sum(sum)
                    .min(min)
                    .max(max)
                    .attributes(attributes)
                    .timestamp(LocalDateTime.now())
                    .build();
        }
    }

    public ServiceMetrics toDomain() {
        return ServiceMetrics.builder()
                .projectKey(ProjectKey.from(projectKey))
                .serviceName(serviceName)
                .metrics(metrics.stream().map(MetricRequest::toDomain).toList())
                .build();
    }
}
