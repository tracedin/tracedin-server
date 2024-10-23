package com.univ.tracedin.domain.metric;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.project.NetworkTopology.Node;
import com.univ.tracedin.domain.project.ProjectKey;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMetrics {

    private ProjectKey projectKey;
    private String serviceName;
    List<Metric> metrics;

    public Node toNode() {
        return Node.createService(projectKey, serviceName);
    }
}
