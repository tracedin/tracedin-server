package com.univ.tracedin.domain.project;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.alert.exception.ProjectPermissionException;
import com.univ.tracedin.domain.project.ProjectMember.MemberRole;
import com.univ.tracedin.domain.project.exception.InvalidProjectKeyException;
import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectReader projectReader;
    private final ProjectMemberManager projectMemberManager;

    public void validate(ProjectKey projectKey) {
        if (!projectReader.exists(projectKey)) {
            throw InvalidProjectKeyException.EXCEPTION;
        }
    }

    public ProjectMember validate(User user, Project targetProject) {
        List<ProjectMember> projectMembers = projectMemberManager.readAll(targetProject);

        return projectMembers.stream()
                .filter(member -> member.equals(user))
                .findFirst()
                .orElseThrow(() -> ProjectPermissionException.EXCEPTION);
    }

    public void validate(User user, Project targetProject, MemberRole role) {
        ProjectMember validatedMember = validate(user, targetProject);

        if (!validatedMember.hasAuthority(role)) {
            throw ProjectPermissionException.EXCEPTION;
        }
    }
}
