package com.univ.tracedin.domain.span;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpanTree {

    private Span span;
    private List<SpanTree> children;

    public static SpanTree init(Span span) {
        return SpanTree.builder().span(span).children(new ArrayList<>()).build();
    }

    public SpanId getParentId() {
        return span.getParentId();
    }

    public void addChild(SpanTree child) {
        children.add(child);
    }
}
