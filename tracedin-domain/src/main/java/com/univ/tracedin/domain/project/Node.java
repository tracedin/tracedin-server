package com.univ.tracedin.domain.project;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Node {

    private ProjectKey projectKey;
    private String name;
    private NodeType nodeType;

    public static Node of(ProjectKey projectKey, String serviceName, NodeType nodeType) {
        return new Node(projectKey, serviceName, nodeType);
    }

    public static Node createService(ProjectKey projectKey, String serviceName) {
        return new Node(projectKey, serviceName, NodeType.SERVICE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node node)) {
            return false;
        }

        return Objects.equals(getProjectKey(), node.getProjectKey())
                && Objects.equals(getName(), node.getName())
                && getNodeType() == node.getNodeType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectKey(), getName(), getNodeType());
    }
}
