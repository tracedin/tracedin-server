package com.univ.tracedin.domain.span;

import io.micrometer.common.util.StringUtils;

public record HitMapCondition(String serviceName, String endPointUrl) {

    public boolean hasServiceName() {
        return StringUtils.isNotBlank(serviceName);
    }

    public boolean hasEndPointUrl() {
        return StringUtils.isNotBlank(endPointUrl);
    }
}
