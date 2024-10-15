package com.univ.tracedin.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Node {

    private String projectKey;
    private String name;
    private NodeType nodeType;

    public static Node of(String projectKey, String serviceName, NodeType nodeType) {
        return new Node(projectKey, serviceName, nodeType);
    }
}
