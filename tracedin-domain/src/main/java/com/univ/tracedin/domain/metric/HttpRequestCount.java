package com.univ.tracedin.domain.metric;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record HttpRequestCount(LocalDateTime timestamp, Long httpRequestCount) {

    public static HttpRequestCount of(long endEpochMillis, Long httpRequestCount) {
        LocalDateTime timestamp =
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(endEpochMillis), ZoneId.systemDefault());
        return new HttpRequestCount(timestamp, httpRequestCount);
    }
}
