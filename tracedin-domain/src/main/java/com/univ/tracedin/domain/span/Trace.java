package com.univ.tracedin.domain.span;

import lombok.Builder;

@Builder
public record Trace(
        String traceId,
        String spanId,
        SpanType spanType,
        String endPoint,
        long startEpochMillis,
        long endEpochMillis) {}
