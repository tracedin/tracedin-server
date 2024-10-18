package com.univ.tracedin.infra.anomaly;

import java.io.Serializable;
import java.util.List;

public record AnomalyTraceResult(Boolean isAnomaly, List<String> anomalySpanIds)
        implements Serializable {}
