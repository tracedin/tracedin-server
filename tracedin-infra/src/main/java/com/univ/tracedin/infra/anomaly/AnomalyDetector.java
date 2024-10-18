package com.univ.tracedin.infra.anomaly;

import java.util.List;

import org.springframework.stereotype.Component;

import com.univ.tracedin.domain.span.Span;

@Component
public class AnomalyDetector implements AnomalyDetectionClient {

    @Override
    public AnomalyTraceResult detect(List<Span> traceSpans) {
        return new AnomalyTraceResult(true, List.of());
    }
}
