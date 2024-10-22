package com.univ.tracedin.api.project;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.project.dto.AddMemberRequest;
import com.univ.tracedin.api.project.dto.CreateProjectRequest;
import com.univ.tracedin.api.project.dto.NodeResponse;
import com.univ.tracedin.api.project.dto.ProjectResponse;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.MemberRole;
import com.univ.tracedin.domain.project.NetworkTopology;
import com.univ.tracedin.domain.project.ProjectId;
import com.univ.tracedin.domain.project.ProjectKey;
import com.univ.tracedin.domain.project.ProjectMemberId;
import com.univ.tracedin.domain.project.ProjectService;
import com.univ.tracedin.domain.user.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectApi implements ProjectApiDocs {

    private final ProjectService projectService;

    @PostMapping
    public Response<ProjectKey> createProject(
            @RequestBody CreateProjectRequest request, Long userId) {
        return Response.success(
                projectService.create(UserId.from(userId), request.toProjectInfo()));
    }

    @GetMapping
    public Response<List<ProjectResponse>> projectList(Long userId) {
        List<ProjectResponse> responses =
                projectService.getProjectList(UserId.from(userId)).stream()
                        .map(ProjectResponse::from)
                        .toList();
        return Response.success(responses);
    }

    @DeleteMapping("/{projectId}")
    public Response<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(ProjectId.from(projectId));
        return Response.success();
    }

    @GetMapping("/{projectKey}/service-nodes")
    public Response<List<NodeResponse>> serviceNodes(@PathVariable String projectKey) {
        List<NodeResponse> responses =
                projectService.getServiceNodeList(ProjectKey.from(projectKey)).stream()
                        .map(NodeResponse::from)
                        .toList();
        return Response.success(responses);
    }

    @GetMapping("/{projectKey}/network-topology")
    public Response<NetworkTopology> networkTopology(@PathVariable String projectKey) {
        return Response.success(projectService.getNetworkTopology(ProjectKey.from(projectKey)));
    }

    @GetMapping("/{projectKey}/hit-map")
    public Response<List<EndTimeBucket>> hitMap(
            @PathVariable String projectKey, @RequestParam(required = false) String serviceName) {
        return Response.success(
                projectService.getTraceHitMap(ProjectKey.from(projectKey), serviceName));
    }

    @PostMapping("/{projectId}/members")
    public Response<Void> addMember(@PathVariable Long projectId, AddMemberRequest request) {
        projectService.addMember(
                ProjectId.from(projectId), request.targetMemberEmail(), request.role());
        return Response.success();
    }

    @DeleteMapping("/members/{projectMemberId}")
    public Response<Void> removeMember(@PathVariable Long projectMemberId) {
        projectService.removeMember(ProjectMemberId.from(projectMemberId));
        return Response.success();
    }

    @PatchMapping("/members/{projectMemberId}")
    public Response<Void> changeRole(@PathVariable Long projectMemberId, MemberRole targetRole) {
        projectService.changeRole(ProjectMemberId.from(projectMemberId), targetRole);
        return Response.success();
    }
}
