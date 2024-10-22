package com.univ.tracedin.infra.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univ.tracedin.infra.project.entity.ProjectMemberEntity;

public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMemberEntity, Long> {

    List<ProjectMemberEntity> findByMemberId(Long memberId);

    List<ProjectMemberEntity> findByProjectId(Long projectId);

    Optional<ProjectMemberEntity> findByProjectIdAndMemberId(Long memberId, Long projectId);
}
