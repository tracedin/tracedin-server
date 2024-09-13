package com.univ.tracedin.api.global.exception;

import com.univ.tracedin.common.exception.WebException;

public class NoPermissionException extends WebException {

    public static final NoPermissionException EXCEPTION = new NoPermissionException();

    private NoPermissionException() {
        super(GlobalErrorCode.PERMISSION_DENIED);
    }
}
