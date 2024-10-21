package com.univ.tracedin.domain.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    private ProjectId id;

    private String name;

    private String description;

    private ProjectKey projectKey;

    public static Project create(ProjectInfo projectInfo) {
        return Project.builder()
                .name(projectInfo.projectName())
                .description(projectInfo.description())
                .projectKey(ProjectKey.create(projectInfo))
                .build();
    }
}
