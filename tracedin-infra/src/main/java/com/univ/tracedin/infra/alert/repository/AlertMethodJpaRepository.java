package com.univ.tracedin.infra.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univ.tracedin.infra.alert.entity.AlertMethodEntity;

public interface AlertMethodJpaRepository extends JpaRepository<AlertMethodEntity, Long> {

    List<AlertMethodEntity> findAllByProjectId(Long userId);
}
