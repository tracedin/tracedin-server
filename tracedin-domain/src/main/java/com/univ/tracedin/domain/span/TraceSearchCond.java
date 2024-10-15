package com.univ.tracedin.domain.span;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.univ.tracedin.domain.project.Node;

public record TraceSearchCond(Node serviceNode, LocalDateTime startTime, LocalDateTime endTime) {

    public long getEpochMillisStartTime() {
        return startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public long getEpochMillisEndTime() {
        return endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean hasTimeRange() {
        return startTime != null && endTime != null;
    }
}
