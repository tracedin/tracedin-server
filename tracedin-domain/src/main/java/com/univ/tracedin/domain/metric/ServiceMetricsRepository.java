package com.univ.tracedin.domain.metric;

import java.util.List;

public interface ServiceMetricsRepository {

    void saveAll(List<ServiceMetrics> metrics);
}
