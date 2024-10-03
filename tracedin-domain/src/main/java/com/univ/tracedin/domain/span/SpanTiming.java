package com.univ.tracedin.domain.span;

import lombok.Builder;

@Builder
public record SpanTiming(Long startEpochMillis, Long endEpochMillis) {}
