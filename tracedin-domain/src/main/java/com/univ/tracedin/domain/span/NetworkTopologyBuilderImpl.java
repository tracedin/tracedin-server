package com.univ.tracedin.domain.span;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.Edge;
import com.univ.tracedin.domain.project.NetworkTopology;
import com.univ.tracedin.domain.project.NetworkTopologyBuilder;
import com.univ.tracedin.domain.project.ServiceNode;

@Component
@RequiredArgsConstructor
public class NetworkTopologyBuilderImpl implements NetworkTopologyBuilder {

    private final SpanReader spanReader;

    @Override
    public NetworkTopology build(String projectKey) {
        List<Span> clientSpans = spanReader.read(projectKey, SpanKind.CLIENT);
        List<Span> serverSpans = spanReader.read(projectKey, SpanKind.SERVER);
        return calculate(projectKey, clientSpans, serverSpans);
    }

    public NetworkTopology calculate(
            String projectKey, List<Span> clientSpans, List<Span> serverSpans) {
        // CLIENT 스팬을 traceId와 spanId로 매핑
        Map<TraceId, Map<SpanId, Span>> clientSpanMap =
                clientSpans.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Span::getTraceId,
                                        Collectors.toMap(Span::getId, Function.identity())));

        // 노드 및 엣지 데이터를 저장할 맵
        Map<String, ServiceNode> nodeMap = new HashMap<>();
        Map<String, Edge> edgeMap = new HashMap<>();

        for (Span serverSpan : serverSpans) {
            TraceId traceId = serverSpan.getTraceId();
            SpanId serverParentId = serverSpan.getParentId();

            Map<SpanId, Span> clientSpansInTrace = clientSpanMap.get(traceId);
            if (clientSpansInTrace == null) {
                continue;
            }

            Span clientSpan = clientSpansInTrace.get(serverParentId);

            if (clientSpan != null) {
                String sourceService = clientSpan.getServiceName();
                String targetService = serverSpan.getServiceName();

                // 노드 추가
                nodeMap.computeIfAbsent(
                        sourceService, service -> ServiceNode.of(projectKey, service));
                nodeMap.computeIfAbsent(
                        targetService, service -> ServiceNode.of(projectKey, service));

                // 엣지 추가 또는 업데이트
                String edgeKey = sourceService + "->" + targetService;
                edgeMap.computeIfAbsent(edgeKey, k -> Edge.init(sourceService, targetService))
                        .incrementRequestCount();
            }
        }

        List<ServiceNode> serviceNodes = new ArrayList<>(nodeMap.values());
        List<Edge> edges = new ArrayList<>(edgeMap.values());

        return NetworkTopology.of(serviceNodes, edges);
    }
}
