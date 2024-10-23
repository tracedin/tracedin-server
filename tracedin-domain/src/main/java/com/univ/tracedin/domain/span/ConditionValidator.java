package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Component;

import com.univ.tracedin.domain.project.TraceSearchCondition;

@Component
public class ConditionValidator {

    public void validate(TraceSearchCondition cond) {
        if (cond.startTime() == null && cond.endTime() != null) {
            throw new IllegalArgumentException("Invalid time condition");
        }

        if (cond.hasTimeRange() && cond.getEpochMillisStartTime() > cond.getEpochMillisEndTime()) {
            throw new IllegalArgumentException("Invalid time condition");
        }
    }
}
