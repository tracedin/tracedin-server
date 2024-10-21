package com.univ.tracedin.infra.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univ.tracedin.domain.project.ProjectId;
import com.univ.tracedin.infra.project.entity.ProjectEntity;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, ProjectId> {

    List<ProjectEntity> findByOwnerId(Long ownerId);

    Optional<ProjectEntity> findByProjectKey(String projectKey);
}
