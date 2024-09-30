package com.univ.tracedin.domain.auth.exception;

import com.univ.tracedin.common.exception.DomainException;

public class PasswordValidationException extends DomainException {

    public static final PasswordValidationException EXCEPTION = new PasswordValidationException();

    private PasswordValidationException() {
        super(AuthErrorCode.PASSWORD_INCORRECT);
    }
}
