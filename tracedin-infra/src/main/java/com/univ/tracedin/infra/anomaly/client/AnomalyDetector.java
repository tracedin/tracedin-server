package com.univ.tracedin.infra.anomaly.client;

import java.util.List;

import org.springframework.stereotype.Component;

import com.univ.tracedin.domain.span.Span;

@Component
public class AnomalyDetector implements AnomalyDetectionClient {

    @Override
    public AnomalyTraceResult detect(List<Span> traceSpans) {
        return new AnomalyTraceResult(
                true,
                "1206887328-a7863a66-528e-4f37-b805-04e1314fb924",
                traceSpans.stream().map(span -> span.getId().getValue()).toList());
    }
}
