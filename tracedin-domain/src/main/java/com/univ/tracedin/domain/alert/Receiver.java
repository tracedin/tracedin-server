package com.univ.tracedin.domain.alert;

import com.univ.tracedin.domain.project.ProjectId;

public record Receiver(ProjectId projectId) {

    public static Receiver from(Long userId) {
        return new Receiver(ProjectId.from(userId));
    }

    public static Receiver from(ProjectId userId) {
        return new Receiver(userId);
    }
}
