package com.univ.tracedin.domain.span;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.StatusCodeDistribution;
import com.univ.tracedin.domain.project.StatusCodeDistributionReader;
import com.univ.tracedin.domain.project.TraceSearchCondition;

@Component
@RequiredArgsConstructor
public class StatusCodeDistributionReaderImpl implements StatusCodeDistributionReader {

    private final SpanRepository spanRepository;

    @Override
    public StatusCodeDistribution read(TraceSearchCondition cond) {
        return spanRepository.getStatusCodeDistribution(cond);
    }
}
