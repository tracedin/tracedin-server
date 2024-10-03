package com.univ.tracedin.domain.project;

import java.util.UUID;

public record ProjectKey(String value) {

    public static ProjectKey create(ProjectInfo projectInfo) {
        return new ProjectKey(projectInfo.projectName().hashCode() + "-" + UUID.randomUUID());
    }

    public static ProjectKey from(String value) {
        return new ProjectKey(value);
    }
}
