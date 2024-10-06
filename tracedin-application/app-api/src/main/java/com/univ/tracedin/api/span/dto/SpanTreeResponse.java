package com.univ.tracedin.api.span.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.univ.tracedin.domain.span.SpanTree;

@JsonInclude(value = NON_EMPTY)
public record SpanTreeResponse(SpanResponse span, List<SpanTreeResponse> children) {

    public static SpanTreeResponse from(SpanTree spanTree) {
        return new SpanTreeResponse(
                SpanResponse.from(spanTree.getSpan()),
                spanTree.getChildren().stream().map(SpanTreeResponse::from).toList());
    }
}
