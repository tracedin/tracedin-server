package com.univ.tracedin.domain.span;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Span {

    private String name;
    private String serviceName;
    private String projectKey;
    private SpanIds spanIds;
    private SpanKind kind;
    private SpanType spanType;
    private SpanTiming timing;
    private SpanAttributes attributes;
}
