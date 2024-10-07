package com.univ.tracedin.domain.metric;

public enum MetricType {
    LONG_GAUGE,
    DOUBLE_GAUGE,
    LONG_SUM,
    DOUBLE_SUM,
    SUMMARY,
    HISTOGRAM,
    EXPONENTIAL_HISTOGRAM;

    public static MetricType fromValue(String value) {
        for (MetricType type : MetricType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown MetricType: " + value);
    }
}
