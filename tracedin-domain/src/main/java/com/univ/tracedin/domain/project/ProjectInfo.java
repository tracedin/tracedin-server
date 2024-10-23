package com.univ.tracedin.domain.project;

public record ProjectInfo(String projectName, String description) {

    public static ProjectInfo of(String projectName, String description) {
        return new ProjectInfo(projectName, description);
    }
}
