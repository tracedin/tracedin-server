package com.univ.tracedin.api.span.dto;

import java.time.LocalDateTime;

import com.univ.tracedin.domain.project.Node;
import com.univ.tracedin.domain.project.NodeType;
import com.univ.tracedin.domain.span.TraceSearchCond;

public record ReadTraceRequest(
        String projectKey, String serviceName, LocalDateTime startTime, LocalDateTime endTime) {

    public TraceSearchCond toSearchCond() {
        return new TraceSearchCond(
                Node.of(projectKey, serviceName, NodeType.SERVICE), startTime, endTime);
    }
}
