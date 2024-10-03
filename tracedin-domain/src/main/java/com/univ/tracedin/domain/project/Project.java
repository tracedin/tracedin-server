package com.univ.tracedin.domain.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.user.User;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    private ProjectId id;

    private String name;

    private String description;

    private ProjectKey projectKey;

    private ProjectOwner owner;

    public static Project create(User user, ProjectInfo projectInfo) {
        return Project.builder()
                .name(projectInfo.projectName())
                .description(projectInfo.description())
                .projectKey(ProjectKey.create(projectInfo))
                .owner(ProjectOwner.from(user))
                .build();
    }
}
