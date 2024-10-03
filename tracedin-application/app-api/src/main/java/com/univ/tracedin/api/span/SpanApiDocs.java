package com.univ.tracedin.api.span;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.span.dto.AppendSpanRequest;
import com.univ.tracedin.api.span.dto.ReadSpanRequest;
import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.span.Trace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "스팬 API")
public interface SpanApiDocs {

    @Operation(summary = "스팬 데이터 추가 API(라이브러리 전용)", description = "라이브러리에서 수집한 스팬 데이터를 추가합니다.")
    void appendSpan(AppendSpanRequest request);

    @Operation(summary = "트레이스(트랜잭션) 조회 API", description = "프로젝트의 특정 서비스의 트레이스(트랜잭션)를 조회합니다.")
    Response<SearchResult<Trace>> getTraces(ReadSpanRequest request, SearchCursor cursor);
}
