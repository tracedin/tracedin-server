package com.univ.tracedin.domain.span;

public enum SpanKind {
    CLIENT,
    SERVER,
    PRODUCER,
    CONSUMER,
    INTERNAL;

    public static SpanKind fromValue(String value) {
        for (SpanKind type : SpanKind.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown span kind: " + value);
    }
}
