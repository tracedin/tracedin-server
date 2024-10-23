package com.univ.tracedin.api.project.dto;

import com.univ.tracedin.domain.project.ProjectMember.MemberRole;

public record AddMemberRequest(String targetMemberEmail, MemberRole role) {}
