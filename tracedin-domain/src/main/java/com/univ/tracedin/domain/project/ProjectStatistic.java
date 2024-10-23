package com.univ.tracedin.domain.project;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectStatistic<T> {

    private T statistic;
    private StatisticsType statisticsType;

    public ProjectStatistic(T statistic, StatisticsType statisticsType) {
        this.statistic = statistic;
        this.statisticsType = statisticsType;
    }

    public static <T> ProjectStatistic<T> of(T statistic, StatisticsType statisticsType) {
        return new ProjectStatistic<>(statistic, statisticsType);
    }

    public enum StatisticsType {
        HTTP_TPS,
        TRACE_HIT_MAP,
        STATUS_CODE
    }
}
