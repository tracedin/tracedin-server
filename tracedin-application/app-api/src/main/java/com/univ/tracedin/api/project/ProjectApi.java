package com.univ.tracedin.api.project;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.project.dto.CreateProjectRequest;
import com.univ.tracedin.domain.auth.UserPrincipal;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.NetworkTopology;
import com.univ.tracedin.domain.project.ProjectKey;
import com.univ.tracedin.domain.project.ProjectService;
import com.univ.tracedin.domain.project.ServiceNode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectApi implements ProjectApiDocs {

    private final ProjectService projectService;

    @PostMapping
    public Response<ProjectKey> createProject(
            @RequestBody CreateProjectRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return Response.success(
                projectService.create(currentUser.userId(), request.toProjectInfo()));
    }

    @GetMapping("/{projectKey}/service-nodes")
    public Response<List<ServiceNode>> serviceNodes(@PathVariable String projectKey) {
        return Response.success(projectService.getServiceNodeList(projectKey));
    }

    @GetMapping("/{projectKey}/network-topology")
    public Response<NetworkTopology> networkTopology(@PathVariable String projectKey) {
        return Response.success(projectService.getNetworkTopology(projectKey));
    }

    @GetMapping("/{projectKey}/hit-map")
    public Response<List<EndTimeBucket>> hitMap(@PathVariable String projectKey) {
        return Response.success(projectService.getTraceHitMap(projectKey));
    }
}
