package com.univ.tracedin.domain.project;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.exception.ProjectPermissionException;
import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectMemberManager projectMemberManager;

    public ProjectMember validate(User user, Project targetProject) {
        List<ProjectMember> projectMembers = projectMemberManager.read(targetProject);

        return projectMembers.stream()
                .filter(member -> member.isMember(user))
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
