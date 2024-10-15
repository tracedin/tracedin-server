package com.univ.tracedin.domain.project;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectReader {

    private final ProjectRepository projectRepository;

    public List<Project> read(ProjectOwner projectOwner) {
        return projectRepository.getByOwner(projectOwner);
    }

    public List<Node> readServiceNods(String projectKey) {
        return projectRepository.findServiceNodeList(projectKey);
    }
}
