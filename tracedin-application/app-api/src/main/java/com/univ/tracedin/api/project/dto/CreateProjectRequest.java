package com.univ.tracedin.api.project.dto;

import com.univ.tracedin.domain.project.ProjectInfo;

public record CreateProjectRequest(String projectName, String description) {

    public ProjectInfo toProjectInfo() {
        return new ProjectInfo(projectName, description);
    }
}
