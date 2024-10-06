package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.HitMapReader;

@Component
@RequiredArgsConstructor
public class HitMapReaderImpl implements HitMapReader {

    private final SpanRepository spanRepository;

    @Override
    public List<EndTimeBucket> read(String projectKey) {
        return spanRepository.getTraceHitMapByProjectKey(projectKey);
    }
}
