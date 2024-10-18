package com.univ.tracedin.api.global.sse;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

    private final Map<Object, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Object key, SseEmitter emitter) {
        emitters.put(key, emitter);
    }

    public Optional<SseEmitter> findById(Object key) {
        return Optional.ofNullable(emitters.get(key));
    }

    public void remove(Object key) {
        emitters.remove(key);
    }
}
