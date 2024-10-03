package com.univ.tracedin.api.span.dto;

import com.univ.tracedin.domain.project.ServiceNode;

public record ReadSpanRequest(String projectKey, String serviceName) {

    public ServiceNode toServiceNode() {
        return ServiceNode.of(projectKey(), serviceName());
    }
}
