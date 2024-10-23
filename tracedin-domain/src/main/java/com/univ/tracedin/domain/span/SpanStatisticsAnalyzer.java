package com.univ.tracedin.domain.span;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.NetworkTopology;
import com.univ.tracedin.domain.project.NetworkTopology.Edge;
import com.univ.tracedin.domain.project.NetworkTopology.Node;
import com.univ.tracedin.domain.project.NetworkTopology.NodeType;
import com.univ.tracedin.domain.project.NetworkTopologyBuilder;
import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectAnalyzer;
import com.univ.tracedin.domain.project.ProjectKey;
import com.univ.tracedin.domain.project.ProjectStatistic;
import com.univ.tracedin.domain.project.ProjectStatistic.StatisticsType;
import com.univ.tracedin.domain.project.TraceSearchCondition;

@Component
@RequiredArgsConstructor
public class SpanStatisticsAnalyzer implements ProjectAnalyzer, NetworkTopologyBuilder {

    private static final String KAFKA_NODE_NAME = "KAFKA";
    private final SpanReader spanReader;
    private final SpanRepository spanRepository;

    @Override
    public ProjectStatistic<?> analyze(TraceSearchCondition cond, StatisticsType statisticsType) {
        return switch (statisticsType) {
            case HTTP_TPS ->
                    ProjectStatistic.of(spanRepository.getHttpTps(cond), StatisticsType.HTTP_TPS);
            case TRACE_HIT_MAP ->
                    ProjectStatistic.of(
                            spanRepository.getTraceHitMap(cond), StatisticsType.TRACE_HIT_MAP);
            case STATUS_CODE ->
                    ProjectStatistic.of(
                            spanRepository.getStatusCodeDistribution(cond),
                            StatisticsType.STATUS_CODE);
        };
    }

    @Override
    public NetworkTopology build(Project project) {
        List<Span> clientSpans =
                spanReader.read(project.getProjectKey(), SpanType.HTTP, SpanKind.CLIENT);
        List<Span> serverSpans =
                spanReader.read(project.getProjectKey(), SpanType.HTTP, SpanKind.SERVER);
        List<Span> producerSpans =
                spanReader.read(project.getProjectKey(), SpanType.UNKNOWN, SpanKind.PRODUCER);
        List<Span> consumerSpans =
                spanReader.read(project.getProjectKey(), SpanType.UNKNOWN, SpanKind.CONSUMER);
        List<Span> dbSpans =
                spanReader.read(project.getProjectKey(), SpanType.QUERY, SpanKind.CLIENT);

        Map<String, Node> nodeMap = new HashMap<>();
        Map<String, Edge> edgeMap = new HashMap<>();

        buildServiceNodesAndEdges(
                project.getProjectKey(), clientSpans, serverSpans, nodeMap, edgeMap);
        buildKafkaNodesAndEdges(
                project.getProjectKey(), producerSpans, consumerSpans, nodeMap, edgeMap);
        buildDatabaseNodesAndEdges(project.getProjectKey(), dbSpans, nodeMap, edgeMap);

        List<Node> nodes = nodeMap.values().stream().toList();
        List<Edge> edges = edgeMap.values().stream().toList();

        return NetworkTopology.of(nodes, edges);
    }

    private void buildServiceNodesAndEdges(
            ProjectKey projectKey,
            List<Span> clientSpans,
            List<Span> serverSpans,
            Map<String, Node> nodeMap,
            Map<String, Edge> edgeMap) {

        Map<TraceId, Map<SpanId, Span>> clientSpanMap =
                clientSpans.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Span::getTraceId,
                                        Collectors.toMap(Span::getId, Function.identity())));

        for (Span serverSpan : serverSpans) {
            Map<SpanId, Span> clientSpansInTrace = clientSpanMap.get(serverSpan.getTraceId());
            if (clientSpansInTrace == null) {
                continue;
            }

            Span clientSpan = clientSpansInTrace.get(serverSpan.getParentId());
            if (clientSpan != null) {
                String sourceService = clientSpan.getServiceName();
                String targetService = serverSpan.getServiceName();

                nodeMap.computeIfAbsent(
                        sourceService, service -> Node.of(projectKey, service, NodeType.SERVICE));
                nodeMap.computeIfAbsent(
                        targetService, service -> Node.of(projectKey, service, NodeType.SERVICE));

                String edgeKey = sourceService + "->" + targetService;
                edgeMap.computeIfAbsent(edgeKey, k -> Edge.init(sourceService, targetService))
                        .incrementRequestCount();
            }
        }
    }

    private void buildKafkaNodesAndEdges(
            ProjectKey projectKey,
            List<Span> producerSpans,
            List<Span> consumerSpans,
            Map<String, Node> nodeMap,
            Map<String, Edge> edgeMap) {

        // Kafka 노드 추가
        nodeMap.computeIfAbsent(
                KAFKA_NODE_NAME, kafka -> Node.of(projectKey, kafka, NodeType.KAFKA));

        // 프로듀서 스팬 처리
        for (Span producerSpan : producerSpans) {
            String producerService = producerSpan.getServiceName();

            nodeMap.computeIfAbsent(
                    producerService, service -> Node.of(projectKey, service, NodeType.SERVICE));

            String edgeKey = producerService + "->" + KAFKA_NODE_NAME;
            edgeMap.computeIfAbsent(edgeKey, k -> Edge.init(producerService, KAFKA_NODE_NAME))
                    .incrementRequestCount();
        }

        // 컨슈머 스팬 처리
        for (Span consumerSpan : consumerSpans) {
            String consumerService = consumerSpan.getServiceName();

            nodeMap.computeIfAbsent(
                    consumerService, service -> Node.of(projectKey, service, NodeType.SERVICE));

            String edgeKey = KAFKA_NODE_NAME + "->" + consumerService;
            edgeMap.computeIfAbsent(edgeKey, k -> Edge.init(KAFKA_NODE_NAME, consumerService))
                    .incrementRequestCount();
        }
    }

    private void buildDatabaseNodesAndEdges(
            ProjectKey projectKey,
            List<Span> dbSpans,
            Map<String, Node> nodeMap,
            Map<String, Edge> edgeMap) {

        for (Span dbSpan : dbSpans) {
            Map<String, Object> attributes = dbSpan.getAttributes().data();
            String serviceName = dbSpan.getServiceName();
            String dbSystem = (String) attributes.get("db.system");

            if (dbSystem == null) {
                continue; // db.name 속성이 없으면 스킵
            }

            nodeMap.computeIfAbsent(
                    serviceName, service -> Node.of(projectKey, service, NodeType.SERVICE));

            nodeMap.computeIfAbsent(dbSystem, db -> Node.of(projectKey, db, NodeType.DATABASE));

            String edgeKey = serviceName + "->" + dbSystem;
            edgeMap.computeIfAbsent(edgeKey, k -> Edge.init(serviceName, dbSystem))
                    .incrementRequestCount();
        }
    }
}
