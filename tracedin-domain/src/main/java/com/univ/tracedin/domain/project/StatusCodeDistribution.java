package com.univ.tracedin.domain.project;

import java.util.List;

public record StatusCodeDistribution(List<StatusCodeBucket> statusCodeBuckets) {

    public record StatusCodeBucket(String statusCode, long count) {

        public static StatusCodeBucket of(String statusCode, long count) {
            return new StatusCodeBucket(statusCode, count);
        }
    }

    public static StatusCodeDistribution from(List<StatusCodeBucket> statusCodeBuckets) {
        return new StatusCodeDistribution(statusCodeBuckets);
    }
}
