package com.univ.tracedin.domain.project;

public interface ProjectAnalyzer {

    ProjectStatistic<?> analyze(
            TraceSearchCondition cond, ProjectStatistic.StatisticsType statisticsType);
}
