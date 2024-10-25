package com.univ.tracedin.domain.project;

import static com.univ.tracedin.domain.project.NetworkTopology.*;

import java.util.List;

import com.univ.tracedin.domain.user.User;

public interface ProjectRepository {

    Project save(Project project);

    Project findById(ProjectId projectId);

    List<Project> findAllByIds(List<ProjectId> projectIds);

    List<Node> findServiceNodeList(ProjectKey projectKey);

    Project findByKey(ProjectKey projectKey);

    boolean existsByKey(ProjectKey projectKey);

    ProjectMember saveProjectMember(ProjectMember projectMember);

    ProjectMember findProjectMemberById(ProjectMemberId id);

    List<ProjectMember> findProjectMembersByProject(Project project);

    List<ProjectMember> findProjectMembersByUser(User user);

    void delete(Project project);

    void deleteProjectMember(ProjectMember projectMember);
}
