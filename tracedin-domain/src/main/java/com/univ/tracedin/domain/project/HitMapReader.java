package com.univ.tracedin.domain.project;

import java.util.List;

public interface HitMapReader {

    List<EndTimeBucket> read(Project project, String serviceName);
}
