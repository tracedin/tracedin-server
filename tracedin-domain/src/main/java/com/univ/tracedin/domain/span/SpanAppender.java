package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpanAppender {

    private final SpanRepository spanRepository;

    public void append(List<Span> spans) {
        spanRepository.save(spans);
    }
}
