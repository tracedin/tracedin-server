package com.univ.tracedin.api.global.sse;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.Node;

@Component
@RequiredArgsConstructor
public class SseConnector {

    public static final String CONNECTION_NAME = "CONNECT";
    public static final long TIMEOUT = 30 * 60 * 1000L;

    private final SseEmitterRepository sseEmitterRepository;

    public SseEmitter connect(Node node) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        sseEmitterRepository.save(node, emitter);
        emitter.onTimeout(() -> sseEmitterRepository.remove(node));
        emitter.onCompletion(() -> sseEmitterRepository.remove(node));

        try {
            emitter.send(SseEmitter.event().id("").name(CONNECTION_NAME).data("emitter connected"));
        } catch (IOException e) {
            sseEmitterRepository.remove(node);
            emitter.completeWithError(e);
        }

        return emitter;
    }
}
