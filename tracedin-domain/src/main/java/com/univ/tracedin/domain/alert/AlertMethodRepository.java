package com.univ.tracedin.domain.alert;

import java.util.List;

import com.univ.tracedin.domain.project.ProjectId;

public interface AlertMethodRepository {

    void saveMethod(AlertMethod alertMethod);

    void deleteMethod(AlertMethod alertMethod);

    AlertMethod findMethodById(AlertMethodId alertMethodId);

    List<AlertMethod> findAllMethodByProjectId(ProjectId projectId);
}
