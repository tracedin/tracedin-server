package com.univ.tracedin.api.span.dto;

import java.time.LocalDateTime;

import com.univ.tracedin.domain.span.Trace;

public record TraceResponse(
        String traceId,
        String endPoint,
        String serviceName,
        int statusCode,
        long startEpochMillis,
        long endEpochMillis,
        long duration,
        boolean isAnomaly,
        LocalDateTime startDateTime) {

    public static TraceResponse from(Trace trace) {
        return new TraceResponse(
                trace.getId().getValue(),
                trace.getEndPoint(),
                trace.getServiceName(),
                trace.getStatusCode(),
                trace.getStartEpochMillis(),
                trace.getEndEpochMillis(),
                trace.duration(),
                trace.isAnomaly(),
                trace.startDateTime());
    }
}
