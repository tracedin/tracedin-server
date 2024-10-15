package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpanAppender {

    private final SpanRepository spanRepository;

    public void appendAll(List<Span> spans) {
        spanRepository.saveAll(spans);
    }
}
