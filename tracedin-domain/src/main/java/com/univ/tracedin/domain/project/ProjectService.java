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

    public List<Node> getServiceNodeList(ProjectKey projectKey) {
        Project project = projectReader.readByKey(projectKey);
        return projectReader.readServiceNods(project);
    }

    public NetworkTopology getNetworkTopology(ProjectKey projectKey) {
        Project project = projectReader.readByKey(projectKey);
        return networkTopologyBuilder.build(project);
    }

    public ProjectStatistic<?> getStatistics(
            TraceSearchCondition cond, StatisticsType statisticsType) {
        projectValidator.validate(cond.projectKey());
        return projectAnalyzer.analyze(cond, statisticsType);
    }

    public void addMember(ProjectId projectId, String targetMemberEmail, MemberRole role) {
        Project project = projectReader.read(projectId);
        User targetUser = userReader.read(targetMemberEmail);
        projectMemberManager.add(project, targetUser, role);
    }

    public void removeMember(ProjectMemberId projectMemberId) {
        ProjectMember projectMember = projectMemberManager.read(projectMemberId);
        projectMemberManager.remove(projectMember);
    }

    public void changeRole(ProjectMemberId projectMemberId, MemberRole role) {
        ProjectMember projectMember = projectMemberManager.read(projectMemberId);
        projectMemberManager.changeRole(projectMember, role);
    }

    public void deleteProject(ProjectId projectId) {
        Project project = projectReader.read(projectId);
        projectMemberManager.removeAll(project);
        projectDeleter.delete(project);
    }
}
