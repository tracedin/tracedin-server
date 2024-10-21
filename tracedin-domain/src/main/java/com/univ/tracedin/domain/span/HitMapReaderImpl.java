package com.univ.tracedin.domain.span;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.HitMapReader;
import com.univ.tracedin.domain.project.Project;

@Component
@RequiredArgsConstructor
public class HitMapReaderImpl implements HitMapReader {

    private final SpanRepository spanRepository;

    @Override
    public List<EndTimeBucket> read(Project project, String serviceName) {
        return spanRepository.getTraceHitMapByProjectKey(project.getProjectKey(), serviceName);
    }
}
