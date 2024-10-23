package com.univ.tracedin.domain.project;

import java.time.LocalDateTime;
import java.time.ZoneId;

import io.micrometer.common.util.StringUtils;

public record TraceSearchCondition(
        ProjectKey projectKey,
        String serviceName,
        String endPointUrl,
        LocalDateTime startTime,
        LocalDateTime endTime) {

    public long getEpochMillisStartTime() {
        return startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public long getEpochMillisEndTime() {
        return endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean hasServiceName() {
        return StringUtils.isNotBlank(serviceName);
    }

    public boolean hasTimeRange() {
        return startTime != null && endTime != null;
    }

    public boolean hasEndPointUrl() {
        return StringUtils.isNotBlank(endPointUrl);
    }
}
