package com.univ.tracedin.domain.span;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Builder;

@Builder
public record SpanTiming(long startEpochMillis, long endEpochMillis) {

    public long duration() {
        return endEpochMillis - startEpochMillis;
    }

    public LocalDateTime startDateTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startEpochMillis), ZoneId.systemDefault());
    }
}
