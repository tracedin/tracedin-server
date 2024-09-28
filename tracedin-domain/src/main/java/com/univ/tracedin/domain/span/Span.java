package com.univ.tracedin.domain.span;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Span {

    private String spanId;

    private String serviceName;

    private String traceId;

    private String parentSpanId;

    private String name;

    private String kind;

    private Long startEpochNanos;

    private Long endEpochNanos;

    private Attributes attributes;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attributes {

        private Map<String, Object> data;

        private Integer capacity;

        private Integer totalAddedValues;
    }
}
