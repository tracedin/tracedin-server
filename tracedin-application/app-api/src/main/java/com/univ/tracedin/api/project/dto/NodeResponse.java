package com.univ.tracedin.api.project.dto;

import com.univ.tracedin.domain.project.NetworkTopology.Node;
import com.univ.tracedin.domain.project.NetworkTopology.NodeType;

public record NodeResponse(String projectKey, String name, NodeType nodeType) {

    public static NodeResponse from(Node node) {
        return new NodeResponse(node.getProjectKey().value(), node.getName(), node.getNodeType());
    }
}
