package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpanAppender {

    private final SpanRepository spanRepository;

    public void append(Span span) {
        spanRepository.save(span);
    }
}
