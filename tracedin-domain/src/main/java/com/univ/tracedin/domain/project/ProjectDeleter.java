package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectDeleter {

    private final ProjectRepository projectRepository;
    private final ProjectMemberManager projectMemberManager;

    public void delete(Project targetProject) {
        projectMemberManager.removeAll(targetProject);
        projectRepository.delete(targetProject);
    }
}
