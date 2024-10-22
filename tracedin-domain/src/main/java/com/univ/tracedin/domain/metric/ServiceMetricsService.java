package com.univ.tracedin.domain.metric;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.Node;
import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectReader;
import com.univ.tracedin.domain.project.ProjectValidator;
import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserReader;

@Service
@RequiredArgsConstructor
public class ServiceMetricsService {

    private final ServiceMetricsReader serviceMetricReader;
    private final UserReader userReader;
    private final ProjectReader projectReader;
    private final ProjectValidator projectValidator;
    private final ServiceMetricsMessagePublisher serviceMetricsMessagePublisher;

    public void appendMetrics(ServiceMetrics metrics) {
        serviceMetricsMessagePublisher.publish(ServiceMetricsCollectedEvent.from(metrics));
    }

    public List<HttpRequestCount> getHttpRequestCount(UserId userId, Node node) {
        User user = userReader.read(userId);
        Project project = projectReader.read(node.getProjectKey());
        projectValidator.validate(user, project);
        return serviceMetricReader.readHttpRequestCount(node);
    }
}
