package com.univ.tracedin.infra.span.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.common.dto.SearchCursor;
import com.univ.tracedin.common.dto.SearchResult;
import com.univ.tracedin.domain.project.ServiceNode;
import com.univ.tracedin.domain.span.Span;
import com.univ.tracedin.domain.span.SpanKind;
import com.univ.tracedin.domain.span.SpanRepository;
import com.univ.tracedin.domain.span.Trace;
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

    @Override
    public List<Span> findByProjectKeyAndSpanKind(String projectKey, SpanKind spanKind) {
        return spanElasticSearchRepository.search(projectKey, spanKind).stream()
                .map(SpanDocument::toSpan)
                .toList();
    }

    @Override
    public SearchResult<Trace> findTracesByServiceNode(
            ServiceNode serviceNode, SearchCursor cursor) {
        return spanElasticSearchRepository.findTracesByServiceNode(
                serviceNode.getProjectKey(),
                serviceNode.getName(),
                cursor.size(),
                cursor.afterKey());
    }
}
