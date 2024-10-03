package com.univ.tracedin.domain.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Edge {

    private String source;
    private String target;
    private int requestCount;

    public static Edge init(String source, String target) {
        return new Edge(source, target, 0);
    }

    public void incrementRequestCount() {
        requestCount++;
    }
}
