package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpanUpdater {

    private final SpanRepository spanRepository;

    public void update(Span span) {
        spanRepository.save(span);
    }
}
