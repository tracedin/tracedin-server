package com.univ.tracedin.domain.project;

import java.util.List;

public interface ProjectRepository {

    Project save(Project project);

    List<Project> getByOwner(ProjectOwner owner);

    List<ServiceNode> findServiceNodeList(String projectKey);
}
