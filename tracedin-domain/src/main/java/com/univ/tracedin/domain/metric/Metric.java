package com.univ.tracedin.domain.metric;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Metric {

    private String name;
    private String description;
    private String unit;
    private MetricType type;
    private Double value;
    private Long count;
    private Double sum;
    private Double min;
    private Double max;
    private Map<String, Object> attributes;
    private LocalDateTime timestamp;
}
