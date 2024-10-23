package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.HitMapReader;
import com.univ.tracedin.domain.project.TraceHipMap;
import com.univ.tracedin.domain.project.TraceSearchCondition;

@Component
@RequiredArgsConstructor
public class HitMapReaderImpl implements HitMapReader {

    private final SpanRepository spanRepository;

    @Override
    public TraceHipMap read(TraceSearchCondition cond) {
        return spanRepository.getTraceHitMapByProjectKey(cond);
    }
}
