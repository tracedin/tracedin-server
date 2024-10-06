package com.univ.tracedin.domain.project;

public record ResponseTimeBucket(double responseTime, long count) {

    public static ResponseTimeBucket from(double responseTime, long count) {
        return new ResponseTimeBucket(responseTime, count);
    }
}
