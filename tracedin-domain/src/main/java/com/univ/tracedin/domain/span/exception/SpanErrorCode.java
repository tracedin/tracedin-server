package com.univ.tracedin.domain.span.exception;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.exception.BaseErrorCode;
import com.univ.tracedin.common.exception.ErrorReason;

@RequiredArgsConstructor
public enum SpanErrorCode implements BaseErrorCode {
    ELASTIC_SEARCH_ERROR(500, "SPAN_500_1", "ElasticSearch 서버와의 통신 중 에러가 발생했습니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}
