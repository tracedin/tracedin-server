package com.univ.tracedin.domain.project;

import java.util.List;

import com.univ.tracedin.domain.span.HitMapCondition;

public interface HitMapReader {

    List<EndTimeBucket> read(Project project, HitMapCondition cond);
}
