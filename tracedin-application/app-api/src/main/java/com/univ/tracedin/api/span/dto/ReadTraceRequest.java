package com.univ.tracedin.api.span.dto;

import java.time.LocalDateTime;

import com.univ.tracedin.domain.project.ProjectKey;
import com.univ.tracedin.domain.span.TraceSearchCond;

public record ReadTraceRequest(
        String projectKey,
        String serviceName,
        String endPointUrl,
        LocalDateTime startTime,
        LocalDateTime endTime) {

    public TraceSearchCond toSearchCond() {
        return new TraceSearchCond(
                new ProjectKey(projectKey), serviceName, endPointUrl, startTime, endTime);
    }
}
