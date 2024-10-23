package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectReader projectReader;

    public void validate(Project project, User user) {}

    public void validate(ProjectKey project) {
        projectReader.readByKey(project); // 없은 프로젝트의 경우 예외
    }
}
