package com.univ.tracedin.api.metric;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.metric.dto.AppendServiceMetricsRequest;
import com.univ.tracedin.api.metric.dto.HttpRequestCountResponse;
import com.univ.tracedin.domain.metric.ServiceMetricsService;
import com.univ.tracedin.domain.project.ServiceNode;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class ServiceMetricsApi implements ServiceMetricsApiDocs {

    private final ServiceMetricsService serviceMetricService;

    @PostMapping
    public void appendMetrics(@RequestBody AppendServiceMetricsRequest requests) {
        serviceMetricService.appendMetrics(requests.toDomain());
    }

    @GetMapping("/http-request-count")
    public Response<List<HttpRequestCountResponse>> getHttpRequestCount(ServiceNode serviceNode) {
        List<HttpRequestCountResponse> responses =
                serviceMetricService.getHttpRequestCount(serviceNode).stream()
                        .map(HttpRequestCountResponse::from)
                        .toList();
        return Response.success(responses);
    }
}
