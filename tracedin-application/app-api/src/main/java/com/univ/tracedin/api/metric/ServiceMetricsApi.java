package com.univ.tracedin.api.metric;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.global.sse.SseConnector;
import com.univ.tracedin.api.metric.dto.AppendServiceMetricsRequest;
import com.univ.tracedin.api.metric.dto.HttpRequestCountResponse;
import com.univ.tracedin.domain.metric.ServiceMetricsService;
import com.univ.tracedin.domain.project.Node;

@RestController
@RequestMapping("/api/v1/service-metrics")
@RequiredArgsConstructor
public class ServiceMetricsApi implements ServiceMetricsApiDocs {

    private final ServiceMetricsService serviceMetricService;
    private final SseConnector<Node> sseConnector;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(String projectKey, String serviceName) {
        SseEmitter emitter = sseConnector.connect(Node.createService(projectKey, serviceName));
        return ResponseEntity.ok(emitter);
    }

    @PostMapping
    public void appendMetrics(@RequestBody AppendServiceMetricsRequest requests) {
        serviceMetricService.appendMetrics(requests.toDomain());
    }

    @GetMapping("/http-request-count")
    public Response<List<HttpRequestCountResponse>> getHttpRequestCount(Node node) {
        List<HttpRequestCountResponse> responses =
                serviceMetricService.getHttpRequestCount(node).stream()
                        .map(HttpRequestCountResponse::from)
                        .toList();
        return Response.success(responses);
    }
}
