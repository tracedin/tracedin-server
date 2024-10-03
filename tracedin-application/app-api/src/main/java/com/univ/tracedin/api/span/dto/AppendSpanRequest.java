package com.univ.tracedin.api.span.dto;

import java.util.Map;

import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanAttributes;
import com.univ.tracedin.domain.span.SpanIds;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanTiming;
import com.univ.tracedin.domain.span.SpanType;

public record AppendSpanRequest(
        String serviceName,
        String projectKey,
        String traceId,
        String spanId,
        String parentSpanId,
        String spanType,
        String name,
        String kind,
        long startEpochNanos,
        long endEpochNanos,
        Attributes attributes) {

    public record Attributes(Map<String, Object> data, int capacity, int totalAddedValues) {

        public SpanAttributes toDomain() {
            return SpanAttributes.builder()
                    .data(data)
                    .capacity(capacity)
                    .totalAddedValues(totalAddedValues)
                    .build();
        }
    }

    public Span toSpan() {
        SpanType type = spanType == null ? null : SpanType.fromValue(spanType);
        return Span.builder()
                .name(name)
                .serviceName(serviceName)
                .projectKey(projectKey)
                .spanIds(
                        SpanIds.builder()
                                .spanId(spanId)
                                .traceId(traceId)
                                .parentSpanId(parentSpanId)
                                .build())
                .spanType(type)
                .kind(SpanKind.fromValue(kind))
                .timing(
                        SpanTiming.builder()
                                .startEpochMillis(nanosToMillis(startEpochNanos))
                                .endEpochMillis(nanosToMillis(endEpochNanos))
                                .build())
                .attributes(attributes.toDomain())
                .build();
    }

    private long nanosToMillis(long epochNanos) {
        return epochNanos / 1_000_000L; // 나노초를 밀리초로 변환
    }
}
