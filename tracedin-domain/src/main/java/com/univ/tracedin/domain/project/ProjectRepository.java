package com.univ.tracedin.domain.project;

import java.util.List;

import com.univ.tracedin.domain.user.User;

public interface ProjectRepository {

    Project save(Project project);

    List<Node> findServiceNodeList(ProjectKey projectKey);

    Project findByKey(ProjectKey projectKey);

    ProjectMember saveProjectMember(ProjectMember projectMember);

    void deleteProjectMember(ProjectMember projectMember);

    ProjectMember findProjectMemberById(ProjectMemberId id);

    Project findById(ProjectId projectId);

    List<ProjectMember> findProjectMembersByUser(User user);

    List<Project> findAllByIds(List<ProjectId> projectIds);

    List<ProjectMember> findProjectMembersByProject(Project project);

    void findProjectMemberByUserAndProject(User user, Project project);
}
