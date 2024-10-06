package com.univ.tracedin.api.span.dto;

import java.util.Map;

import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanType;

public record SpanResponse(
        String id,
        String traceId,
        String parentSpanId,
        String name,
        String serviceName,
        String projectKey,
        SpanKind kind,
        SpanType spanType,
        Long startEpochMillis,
        Long endEpochMillis,
        Map<String, Object> data,
        Integer capacity,
        Integer totalAddedValues) {

    public static SpanResponse from(Span span) {
        return new SpanResponse(
                span.getId().getValue(),
                span.getTraceId().getValue(),
                span.getParentId().getValue(),
                span.getName(),
                span.getServiceName(),
                span.getProjectKey(),
                span.getKind(),
                span.getSpanType(),
                span.getTiming().startEpochMillis(),
                span.getTiming().endEpochMillis(),
                span.getAttributes().data(),
                span.getAttributes().capacity(),
                span.getAttributes().totalAddedValues());
    }
}
