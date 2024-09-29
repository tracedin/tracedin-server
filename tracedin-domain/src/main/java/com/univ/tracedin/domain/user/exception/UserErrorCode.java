package com.univ.tracedin.domain.user.exception;

import static com.univ.tracedin.common.constants.TracedInConstants.NOT_FOUND;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.exception.BaseErrorCode;
import com.univ.tracedin.common.exception.ErrorReason;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "해당 ID의 유저의 정보를 찾지 못했습니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}
