package com.univ.tracedin.domain.auth.exception;

import com.univ.tracedin.common.exception.DomainException;

public class AuthenticationException extends DomainException {

    public static final AuthenticationException EXCEPTION = new AuthenticationException();

    private AuthenticationException() {
        super(AuthErrorCode.AUTHENTICATION_FAILED);
    }
}
