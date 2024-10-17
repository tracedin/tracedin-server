package com.univ.tracedin.api.global.sse;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.univ.tracedin.domain.project.Node;

@Repository
public class SseEmitterRepository {

    private final Map<Node, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Node node, SseEmitter emitter) {
        emitters.put(node, emitter);
    }

    public Optional<SseEmitter> findById(Node node) {
        return Optional.ofNullable(emitters.get(node));
    }

    public void remove(Node node) {
        emitters.remove(node);
    }
}
