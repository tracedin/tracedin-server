package com.univ.tracedin.infra.span.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanId;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanRepository;
import com.univ.tracedin.domain.span.SpanType;
import com.univ.tracedin.domain.span.Trace;
import com.univ.tracedin.domain.span.TraceId;
import com.univ.tracedin.domain.span.TraceSearchCond;
import com.univ.tracedin.infra.span.document.SpanDocument;

@Transactional
@Repository
@RequiredArgsConstructor
public class SpanCoreRepository implements SpanRepository {

    private final SpanElasticSearchRepository spanElasticSearchRepository;

    @Override
    public void saveAll(List<Span> spans) {
        spanElasticSearchRepository.saveAll(spans.stream().map(SpanDocument::from).toList());
    }

    @Override
    public void save(Span span) {
        spanElasticSearchRepository.save(SpanDocument.from(span));
    }

    @Override
    public Span findById(SpanId id) {
        return spanElasticSearchRepository
                .findById(id.getValue())
                .map(SpanDocument::toSpan)
                .orElseThrow(() -> new RuntimeException("Span not found"));
    }

    @Override
    public List<Span> findByProjectKeyAndSpanKind(
            String projectKey, SpanType spanType, SpanKind spanKind) {
        return spanElasticSearchRepository.search(projectKey, spanType, spanKind).stream()
                .map(SpanDocument::toSpan)
                .toList();
    }

    @Override
    public SearchResult<Trace> findTracesByNode(TraceSearchCond cond, SearchCursor cursor) {
        return spanElasticSearchRepository.findTracesByNode(cond, cursor.size(), cursor.afterKey());
    }

    @Override
    public List<Span> findByTraceId(TraceId traceId) {
        return spanElasticSearchRepository.findByTraceId(traceId.getValue()).stream()
                .map(SpanDocument::toSpan)
                .toList();
    }

    @Override
    public List<EndTimeBucket> getTraceHitMapByProjectKey(String projectKey, String serviceName) {
        return spanElasticSearchRepository.getTraceHitMapByProjectKey(projectKey, serviceName);
    }
}
