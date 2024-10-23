package com.univ.tracedin.domain.project.exception;

import com.univ.tracedin.common.exception.DomainException;

public class InvalidProjectKeyException extends DomainException {

    public static final InvalidProjectKeyException EXCEPTION = new InvalidProjectKeyException();

    public InvalidProjectKeyException() {
        super(ProjectErrorCode.PROJECT_KEY_NOT_FOUND);
    }
}
