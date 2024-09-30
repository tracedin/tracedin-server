package com.univ.tracedin.domain.auth.exception;

import static com.univ.tracedin.common.constants.TracedInConstants.FORBIDDEN;
import static com.univ.tracedin.common.constants.TracedInConstants.UNAUTHORIZED;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.exception.BaseErrorCode;
import com.univ.tracedin.common.exception.ErrorReason;

@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    PASSWORD_INCORRECT(UNAUTHORIZED, "AUTH_401_1", "패스워드가 일치하지 않습니다."),
    AUTHENTICATION_FAILED(UNAUTHORIZED, "AUTH_401_2", "인증에 실패하였습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "AUTH_401_3", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "AUTH_401_4", "만료된 토큰입니다."),
    PERMISSION_DENIED(FORBIDDEN, "AUTH_403_1", "해당 API 권한이 없습니다"),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}
