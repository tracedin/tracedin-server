package com.univ.tracedin.domain.project;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectReader {

    private final ProjectRepository projectRepository;
    private final ProjectMemberManager projectMemberManager;

    public List<Project> readAll(User user) {
        List<ProjectMember> projectMembers = projectMemberManager.readAll(user);
        List<ProjectId> projectIds =
                projectMembers.stream().map(ProjectMember::getProjectId).toList();
        return projectRepository.findAllByIds(projectIds);
    }

    public boolean exists(ProjectKey projectKey) {
        return projectRepository.existsByKey(projectKey);
    }

    public Project read(ProjectId projectId) {
        return projectRepository.findById(projectId);
    }

    public Project readByKey(ProjectKey projectKey) {
        return projectRepository.findByKey(projectKey);
    }

    public List<NetworkTopology.Node> readServiceNods(Project project) {
        return projectRepository.findServiceNodeList(project.getProjectKey());
    }
}
