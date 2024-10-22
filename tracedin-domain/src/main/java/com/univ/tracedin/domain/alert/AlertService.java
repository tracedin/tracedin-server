package com.univ.tracedin.domain.alert;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.MemberRole;
import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectId;
import com.univ.tracedin.domain.project.ProjectReader;
import com.univ.tracedin.domain.project.ProjectValidator;
import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserReader;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final UserReader userReader;
    private final ProjectReader projectReader;
    private final ProjectValidator projectValidator;
    private final AlertMethodManager alertMethodManager;

    public void appendMethod(UserId userId, AlertInfo alertInfo) {
        User user = userReader.read(userId);
        Project targetProject = projectReader.read(alertInfo.projectId());
        projectValidator.validate(user, targetProject, MemberRole.ADMIN);
        alertMethodManager.append(alertInfo);
    }

    public List<AlertMethod> readAllMethods(UserId userId, ProjectId projectId) {
        User user = userReader.read(userId);
        Project targetProject = projectReader.read(projectId);
        projectValidator.validate(user, targetProject);
        return alertMethodManager.readAll(projectId);
    }

    public void removeMethod(UserId userId, AlertMethodId alertMethodId) {
        User user = userReader.read(userId);
        AlertMethod alertMethod = alertMethodManager.read(alertMethodId);
        Project targetProject = projectReader.read(alertMethod.getProjectId());
        projectValidator.validate(user, targetProject, MemberRole.ADMIN);
        alertMethodManager.remove(alertMethod);
    }

    public void changeMethodStatus(
            UserId userId, AlertMethodId alertMethodId, AlertMethodStatus status) {
        User user = userReader.read(userId);
        AlertMethod alertMethod = alertMethodManager.read(alertMethodId);
        Project targetProject = projectReader.read(alertMethod.getProjectId());
        projectValidator.validate(user, targetProject, MemberRole.ADMIN);
        alertMethodManager.changeStatus(alertMethod, status);
    }
}
