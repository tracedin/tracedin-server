package com.univ.tracedin.infra.anomaly.client;

import java.io.Serializable;
import java.util.List;

public record AnomalyTraceResult(Boolean isAnomaly, String projectKey, List<String> anomalySpanIds)
        implements Serializable {}
