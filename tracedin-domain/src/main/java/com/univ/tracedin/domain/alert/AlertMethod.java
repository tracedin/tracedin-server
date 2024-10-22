package com.univ.tracedin.domain.alert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.univ.tracedin.domain.project.ProjectId;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlertMethod {

    private AlertMethodId id;
    private ProjectId projectId;
    private AlertType alertType;
    private String contact;
    private AlertMethodStatus status;

    public void inactivate() {
        this.status = AlertMethodStatus.INACTIVE;
    }

    public void activate() {
        this.status = AlertMethodStatus.ACTIVE;
    }

    public static AlertMethod create(AlertInfo alertInfo) {
        return AlertMethod.builder()
                .projectId(alertInfo.projectId())
                .alertType(alertInfo.alertType())
                .contact(alertInfo.contact())
                .status(AlertMethodStatus.ACTIVE)
                .build();
    }

    public boolean isActivated() {
        return this.status == AlertMethodStatus.ACTIVE;
    }

    // TODO : 각 타입별 contact 유효성 검증

}
