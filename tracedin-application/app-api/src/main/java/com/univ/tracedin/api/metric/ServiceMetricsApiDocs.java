package com.univ.tracedin.api.metric;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.metric.dto.AppendServiceMetricsRequest;
import com.univ.tracedin.api.metric.dto.HttpRequestCountResponse;
import com.univ.tracedin.domain.project.Node;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "메트릭 API")
public interface ServiceMetricsApiDocs {

    @Operation(summary = "메트릭 SSE 구독", description = "SSE 이벤트를 구독하여 메트릭을 실시간으로 받을 수 있습니다.")
    ResponseEntity<SseEmitter> subscribe(String projectKey, String serviceName);

    @Operation(summary = "메트릭 추가(라이브러리 전용)")
    void appendMetrics(AppendServiceMetricsRequest requests);

    @Operation(
            summary = "5시간 이내의 10분 별로 HTTP 요청 횟수 조회",
            description = "5시간 이내의 10분 별로 HTTP 요청 횟수 조회, 개발 기간에는 5시간 이내 조건 없음")
    Response<List<HttpRequestCountResponse>> getHttpRequestCount(Node node);
}
