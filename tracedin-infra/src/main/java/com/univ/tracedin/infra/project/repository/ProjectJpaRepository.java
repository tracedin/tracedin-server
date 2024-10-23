package com.univ.tracedin.infra.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.univ.tracedin.infra.project.entity.ProjectEntity;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByProjectKey(String projectKey);

    @Query(
            """
                    select p from ProjectEntity p
                    where p.id in :ids
                    """)
    List<ProjectEntity> findAllInIds(List<Long> ids);

    @Query("select exists (select 1 from ProjectEntity p where p.projectKey = :projectKey)")
    boolean existsByProjectKey(String projectKey);
}
