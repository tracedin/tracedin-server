package com.univ.tracedin.api.span;

import java.util.List;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.span.dto.AppendSpanRequest;
import com.univ.tracedin.api.span.dto.ReadTraceRequest;
import com.univ.tracedin.api.span.dto.SpanTreeResponse;
import com.univ.tracedin.api.span.dto.TraceResponse;
import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "스팬 API")
public interface SpanApiDocs {

    @Operation(summary = "스팬 데이터 추가 API(라이브러리 전용)", description = "라이브러리에서 수집한 스팬 데이터를 추가합니다.")
    void appendSpan(List<AppendSpanRequest> request);

    @Operation(summary = "트레이스(트랜잭션) 조회 API", description = "프로젝트의 특정 서비스의 트레이스(트랜잭션)를 조회합니다.")
    Response<SearchResult<TraceResponse>> searchTraces(
            ReadTraceRequest request, SearchCursor cursor);

    @Operation(
            summary = "트레이스(트랜잭션) 내 스팬트리 조회 API",
            description = "특정 트레이스(트랜잭션)의 스팬을 조회하고 계층 형태로 반환합니다.")
    Response<SpanTreeResponse> getSpanTreeByTrace(String traceId);
}
