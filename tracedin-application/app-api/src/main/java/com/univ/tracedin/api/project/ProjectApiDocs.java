package com.univ.tracedin.api.project;

import java.util.List;

import com.univ.tracedin.api.global.dto.Response;
import com.univ.tracedin.api.project.dto.AddMemberRequest;
import com.univ.tracedin.api.project.dto.CreateProjectRequest;
import com.univ.tracedin.api.project.dto.NodeResponse;
import com.univ.tracedin.api.project.dto.ProjectResponse;
import com.univ.tracedin.domain.auth.UserPrincipal;
import com.univ.tracedin.domain.project.EndTimeBucket;
import com.univ.tracedin.domain.project.MemberRole;
import com.univ.tracedin.domain.project.NetworkTopology;
import com.univ.tracedin.domain.project.ProjectKey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "프로젝트 API")
public interface ProjectApiDocs {

    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성하고 프로젝트 키를 반환합니다.")
    Response<ProjectKey> createProject(CreateProjectRequest request, UserPrincipal currentUser);

    @Operation(summary = "프로젝트 리스트 조회", description = "사용자의 프로젝트 리스트를 조회합니다.")
    Response<List<ProjectResponse>> projectList(UserPrincipal currentUser);

    @Operation(summary = "서비스 리스트 조회", description = "프로젝트의 서비스 노드 리스트를 조회합니다.")
    Response<List<NodeResponse>> serviceNodes(String projectKey);

    @Operation(summary = "네트워크 토폴로지 조회", description = "프로젝트의 네트워크 토폴로지를 조회합니다.")
    Response<NetworkTopology> networkTopology(String projectKey);

    @Operation(summary = "히트맵 조회", description = "프로젝트의 히트맵을 조회합니다.(5분 별 트레이스의 응답시간 분포)")
    Response<List<EndTimeBucket>> hitMap(String projectKey, String serviceName);

    @Operation(summary = "프로젝트 멤버 추가", description = "프로젝트에 멤버를 추가합니다.")
    Response<Void> addMember(Long projectId, AddMemberRequest request);

    @Operation(summary = "프로젝트 멤버 삭제", description = "프로젝트의 멤버를 삭제합니다.")
    Response<Void> removeMember(Long projectMemberId);

    @Operation(summary = "프로젝트 멤버 권한 변경", description = "프로젝트의 멤버 권한을 변경합니다.")
    Response<Void> changeRole(Long projectMemberId, MemberRole targetRole);
}
