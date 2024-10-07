package com.univ.tracedin.api.metric.dto;

import java.time.LocalDateTime;

import com.univ.tracedin.domain.metric.HttpRequestCount;

public record HttpRequestCountResponse(LocalDateTime timestamp, Long httpRequestCount) {

    public static HttpRequestCountResponse from(HttpRequestCount httpRequestCount) {
        return new HttpRequestCountResponse(
                httpRequestCount.timestamp(), httpRequestCount.httpRequestCount());
    }
}
