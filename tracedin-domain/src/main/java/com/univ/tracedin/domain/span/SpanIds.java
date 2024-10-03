package com.univ.tracedin.domain.span;

import lombok.Builder;

@Builder
public record SpanIds(String spanId, String traceId, String parentSpanId) {}
