package com.univ.tracedin.api.global.sse.exception;

import static com.univ.tracedin.common.constants.TracedInConstants.INTERNAL_SERVER;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.exception.BaseErrorCode;
import com.univ.tracedin.common.exception.ErrorReason;

@RequiredArgsConstructor
public enum SseErrorCode implements BaseErrorCode {
    SSE_CONNECTION_FAILED(INTERNAL_SERVER, "SSE_500_1", "SSE 연결에 실패했습니다.");

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}
