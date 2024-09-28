package com.univ.tracedin.infra.span.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanRepository;
import com.univ.tracedin.infra.span.document.SpanDocument;

@Transactional
@Repository
@RequiredArgsConstructor
public class SpanCoreRepository implements SpanRepository {

    private final SpanElasticSearchRepository spanElasticSearchRepository;

    @Override
    public void save(Span span) {
        spanElasticSearchRepository.save(SpanDocument.from(span));
    }

    @Override
    public Span findById(String id) {
        return spanElasticSearchRepository
                .findById(id)
                .map(SpanDocument::toSpan)
                .orElseThrow(() -> new RuntimeException("Span not found"));
    }
}
