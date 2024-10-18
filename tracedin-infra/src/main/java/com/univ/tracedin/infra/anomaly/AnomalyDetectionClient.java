package com.univ.tracedin.infra.anomaly;

import java.util.List;

import com.univ.tracedin.domain.span.Span;

public interface AnomalyDetectionClient {

    AnomalyTraceResult detect(List<Span> traceSpans);
}
