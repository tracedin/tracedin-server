package com.univ.tracedin.domain.span;

import java.util.Map;

import lombok.Builder;

@Builder
public record SpanAttributes(
        Map<String, Object> data, Integer capacity, Integer totalAddedValues) {}
