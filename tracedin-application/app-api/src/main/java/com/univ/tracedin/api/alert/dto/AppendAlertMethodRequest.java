package com.univ.tracedin.api.alert.dto;

import com.univ.tracedin.domain.alert.AlertInfo;
import com.univ.tracedin.domain.alert.AlertType;
import com.univ.tracedin.domain.project.ProjectId;

public record AppendAlertMethodRequest(Long projectId, AlertType alertType, String contact) {

    public AlertInfo toAlertInfo() {
        return new AlertInfo(ProjectId.from(projectId), alertType, contact);
    }
}
