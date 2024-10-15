package com.univ.tracedin.domain.project;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkTopology {

    private final List<Node> nodes;
    private final List<Edge> edges;

    public static NetworkTopology of(List<Node> nodes, List<Edge> edges) {
        return new NetworkTopology(nodes, edges);
    }
}
