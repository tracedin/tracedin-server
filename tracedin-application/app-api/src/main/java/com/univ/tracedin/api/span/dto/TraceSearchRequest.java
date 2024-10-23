package com.univ.tracedin.api.span.dto;

import java.time.LocalDateTime;

import com.univ.tracedin.domain.project.ProjectKey;
import com.univ.tracedin.domain.project.TraceSearchCondition;

public record TraceSearchRequest(
        String projectKey,
        String serviceName,
        String endPointUrl,
        LocalDateTime startTime,
        LocalDateTime endTime) {

    public TraceSearchCondition toCondition() {
        return new TraceSearchCondition(
                new ProjectKey(projectKey), serviceName, endPointUrl, startTime, endTime);
    }
}
