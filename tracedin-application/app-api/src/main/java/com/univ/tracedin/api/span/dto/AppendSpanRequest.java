package com.univ.tracedin.api.span.dto;

import java.util.Map;

import com.univ.tracedin.domain.span.Span;

public record AppendSpanRequest(
        String serviceName,
        String traceId,
        String spanId,
        String parentSpanId,
        String name,
        String kind,
        long startEpochNanos,
        long endEpochNanos,
        Attributes attributes) {
    public record Attributes(Map<String, Object> data, int capacity, int totalAddedValues) {}

    public Span toSpan() {
        return Span.builder()
                .serviceName(serviceName)
                .traceId(traceId)
                .spanId(spanId)
                .parentSpanId(parentSpanId)
                .name(name)
                .kind(kind)
                .startEpochNanos(startEpochNanos)
                .endEpochNanos(endEpochNanos)
                .attributes(
                        new Span.Attributes(
                                attributes.data, attributes.capacity, attributes.totalAddedValues))
                .build();
    }
}
