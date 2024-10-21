package com.univ.tracedin.domain.anomaly;

import java.util.List;

import com.univ.tracedin.domain.span.SpanId;
import com.univ.tracedin.domain.span.TraceId;

public record AnomalyTrace(TraceId traceId, String projectKey, List<SpanId> anomalySpanIds) {

    public static AnomalyTrace from(
            TraceId traceId, String projectKey, List<String> anomalySpanIds) {
        List<SpanId> spanIds = anomalySpanIds.stream().map(SpanId::from).toList();
        return new AnomalyTrace(traceId, projectKey, spanIds);
    }
}
