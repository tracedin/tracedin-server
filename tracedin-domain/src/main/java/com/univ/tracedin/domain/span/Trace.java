package com.univ.tracedin.domain.span;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trace {

    private TraceId id;
    private String endPoint;
    private long startEpochMillis;
    private long endEpochMillis;
}
