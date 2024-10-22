package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectDeleter {

    private final ProjectRepository projectRepository;

    public void delete(Project project) {
        projectRepository.delete(project);
    }
}
