package com.univ.tracedin.domain.project;

public interface StatusCodeDistributionReader {

    StatusCodeDistribution read(TraceSearchCondition cond);
}
