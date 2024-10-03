package com.univ.tracedin.domain.project;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserReader;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserReader userReader;
    private final ProjectReader projectReader;
    private final ProjectAppender projectAppender;
    private final NetworkTopologyCalculator networkTopologyCalculator;

    public ProjectKey create(UserId creatorId, ProjectInfo projectInfo) {
        User user = userReader.read(creatorId);
        return projectAppender.append(user, projectInfo);
    }

    public List<ServiceNode> getServiceNodeList(String projectKey) {
        return projectReader.readServiceNods(projectKey);
    }

    public NetworkTopology getNetworkTopology(String projectKey) {
        return networkTopologyCalculator.calculate(projectKey);
    }
}
