package com.univ.tracedin.domain.span;

public interface SpanRepository {

    void save(Span span);

    Span findById(String id);
}
