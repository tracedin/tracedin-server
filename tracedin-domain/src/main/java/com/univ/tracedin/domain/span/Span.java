package com.univ.tracedin.domain.span;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Span {

    private SpanId id;
    private TraceId traceId;
    private SpanId parentId;
    private String name;
    private String serviceName;
    private String projectKey;
    private SpanKind kind;
    private SpanType spanType;
    private SpanTiming timing;
    private SpanStatus status;
    private SpanAttributes attributes;
    private List<SpanEvent> events;

    public boolean hasParent() {
        return !Objects.equals(parentId.getValue(), "0000000000000000");
    }

    public long getDuration() {
        return timing.duration();
    }

    public LocalDateTime getStartDateTime() {
        return timing.startDateTime();
    }
}
