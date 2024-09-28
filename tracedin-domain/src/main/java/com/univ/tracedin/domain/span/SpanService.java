package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpanService {

    private final SpanAppender spanAppender;

    public void appendSpan(Span span) {
        spanAppender.append(span);
    }
}
