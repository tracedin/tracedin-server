package com.univ.tracedin.api.span.dto;

import com.univ.tracedin.domain.span.Trace;

public record TraceResponse(
        String traceId, String endPoint, long startEpochMillis, long endEpochMillis) {

    public static TraceResponse from(Trace trace) {
        return new TraceResponse(
                trace.getId().getValue(),
                trace.getEndPoint(),
                trace.getStartEpochMillis(),
                trace.getEndEpochMillis());
    }
}
