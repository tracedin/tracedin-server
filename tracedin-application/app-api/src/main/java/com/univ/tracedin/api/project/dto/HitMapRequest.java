package com.univ.tracedin.api.project.dto;

import org.springframework.web.bind.annotation.RequestParam;

import com.univ.tracedin.domain.span.HitMapCondition;

public record HitMapRequest(
        @RequestParam(required = false) String serviceName, String endPointUrl) {

    public HitMapCondition toCondition() {
        return new HitMapCondition(serviceName, endPointUrl);
    }
}
