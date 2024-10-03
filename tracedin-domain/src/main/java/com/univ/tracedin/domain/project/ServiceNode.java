package com.univ.tracedin.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceNode {

    private String projectKey;
    private String name;

    public static ServiceNode of(String projectKey, String serviceName) {
        return new ServiceNode(projectKey, serviceName);
    }
}
