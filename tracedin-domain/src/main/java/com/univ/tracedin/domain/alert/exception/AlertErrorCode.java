package com.univ.tracedin.domain.alert.exception;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.exception.BaseErrorCode;
import com.univ.tracedin.common.exception.ErrorReason;

@RequiredArgsConstructor
public enum AlertErrorCode implements BaseErrorCode {
    ALERT_NOT_FOUND(404, "ALERT_404_1", "해당 ID의 알림을 찾지 못했습니다."),
    ALERT_SEND_FAIL(500, "ALERT_500_1", "알림 전송에 실패했습니다."),
    ALERT_METHOD_NOT_FOUND(404, "ALERT_404_2", "해당 ID의 알림 수단을 찾지 못했습니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}
