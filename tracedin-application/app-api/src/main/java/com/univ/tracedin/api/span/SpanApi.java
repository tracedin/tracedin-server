package com.univ.tracedin.api.span;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.span.dto.AppendSpanRequest;
import com.univ.tracedin.api.span.dto.ReadTraceRequest;
import com.univ.tracedin.api.span.dto.SpanTreeResponse;
import com.univ.tracedin.api.span.dto.TraceResponse;
import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.span.SpanService;
import com.univ.tracedin.domain.span.TraceId;

@RestController
@RequestMapping("/api/v1/spans")
@RequiredArgsConstructor
@Slf4j
public class SpanApi implements SpanApiDocs {

    private final SpanService spanService;

    @PostMapping
    public void appendSpan(@RequestBody List<AppendSpanRequest> request) {
        log.info("appendSpan request: {}", request.toString());
        spanService.publishSpans(request.stream().map(AppendSpanRequest::toSpan).toList());
    }

    @GetMapping("/traces")
    public Response<SearchResult<TraceResponse>> searchTraces(
            ReadTraceRequest request, SearchCursor cursor) {
        SearchResult<TraceResponse> responses =
                spanService.getTraces(request.toSearchCond(), cursor).map(TraceResponse::from);
        return Response.success(responses);
    }

    @GetMapping("/span-tree")
    public Response<SpanTreeResponse> getSpanTreeByTrace(String traceId) {
        SpanTreeResponse response =
                SpanTreeResponse.from(spanService.getSpanTree(TraceId.from(traceId)));
        return Response.success(response);
    }
}
