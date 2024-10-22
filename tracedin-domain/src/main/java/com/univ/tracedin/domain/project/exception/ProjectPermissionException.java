package com.univ.tracedin.domain.project.exception;

import com.univ.tracedin.common.exception.DomainException;

public class ProjectPermissionException extends DomainException {

    public static final ProjectPermissionException EXCEPTION = new ProjectPermissionException();

    private ProjectPermissionException() {
        super(ProjectErrorCode.NO_PERMISSION_TO_ACCESS_PROJECT);
    }
}
