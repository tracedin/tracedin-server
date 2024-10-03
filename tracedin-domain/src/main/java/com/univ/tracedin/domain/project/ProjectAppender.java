package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectAppender {

    private final ProjectRepository projectRepository;

    public ProjectKey append(User user, ProjectInfo projectInfo) {
        Project project = Project.create(user, projectInfo);
        projectRepository.save(project);
        return project.getProjectKey();
    }
}
