package com.univ.tracedin.api.project.dto;

import com.univ.tracedin.domain.project.Project;

public record ProjectResponse(Long id, String name, String description, String projectKey) {

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getProjectKey().value());
    }
}
