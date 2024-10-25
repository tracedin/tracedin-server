package com.univ.tracedin.domain.project;

import static com.univ.tracedin.domain.project.NetworkTopology.*;
import static com.univ.tracedin.domain.project.ProjectMember.*;
import static com.univ.tracedin.domain.project.ProjectStatistic.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserReader;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserReader userReader;
    private final ProjectReader projectReader;
    private final ProjectAppender projectAppender;
    private final ProjectDeleter projectDeleter;
    private final ProjectValidator projectValidator;
    private final ProjectMemberManager projectMemberManager;
    private final NetworkTopologyBuilder networkTopologyBuilder;
    private final ProjectAnalyzer projectAnalyzer;

    public ProjectKey create(UserId creatorId, ProjectInfo projectInfo) {
        User user = userReader.read(creatorId);
        return projectAppender.append(user, projectInfo);
    }

    public List<Project> getProjectList(UserId userId) {
        User user = userReader.read(userId);
        return projectReader.readAll(user);
    }

    public List<Node> getServiceNodeList(UserId userId, ProjectKey projectKey) {
        User currentUser = userReader.read(userId);
        Project targetProject = projectReader.read(projectKey);
        projectValidator.validate(currentUser, targetProject);
        return projectReader.readServiceNods(targetProject);
    }

    public NetworkTopology getNetworkTopology(UserId userId, ProjectKey projectKey) {
        User currentUser = userReader.read(userId);
        Project targetProject = projectReader.read(projectKey);
        projectValidator.validate(currentUser, targetProject);
        return networkTopologyBuilder.build(targetProject);
    }

    public ProjectStatistic<?> getStatistics(
            UserId userId, TraceSearchCondition cond, StatisticsType statisticsType) {
        User currentUser = userReader.read(userId);
        Project targetProject = projectReader.read(cond.projectKey());
        projectValidator.validate(currentUser, targetProject);
        return projectAnalyzer.analyze(cond, statisticsType);
    }

    public void addMember(
            UserId userId, ProjectId projectId, String targetMemberEmail, MemberRole role) {
        User currentUser = userReader.read(userId);
        Project project = projectReader.read(projectId);
        User targetUser = userReader.read(targetMemberEmail);
        projectValidator.validate(currentUser, project, MemberRole.ADMIN);
        projectMemberManager.add(project, targetUser, role);
    }

    public void removeMember(UserId userId, ProjectMemberId projectMemberId) {
        User currentUser = userReader.read(userId);
        ProjectMember projectMember = projectMemberManager.read(projectMemberId);
        Project targetProject = projectReader.read(projectMember.getProjectId());
        projectValidator.validate(currentUser, targetProject, MemberRole.ADMIN);
        projectMemberManager.remove(projectMember);
    }

    public void changeRole(UserId userId, ProjectMemberId projectMemberId, MemberRole role) {
        User currentUser = userReader.read(userId);
        ProjectMember targetMember = projectMemberManager.read(projectMemberId);
        Project targetProject = projectReader.read(targetMember.getProjectId());
        projectValidator.validate(currentUser, targetProject, MemberRole.ADMIN);
        projectMemberManager.changeRole(targetMember, role);
    }

    public void deleteProject(UserId userId, ProjectId projectId) {
        User currentUser = userReader.read(userId);
        Project targetProject = projectReader.read(projectId);
        projectValidator.validate(currentUser, targetProject, MemberRole.ADMIN);
        projectDeleter.delete(targetProject);
    }
}
