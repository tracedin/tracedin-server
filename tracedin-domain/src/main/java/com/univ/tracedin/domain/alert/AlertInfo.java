package com.univ.tracedin.domain.alert;

import com.univ.tracedin.domain.project.ProjectId;

public record AlertInfo(ProjectId projectId, AlertType alertType, String contact) {}
