package com.univ.tracedin.infra.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univ.tracedin.infra.alert.entity.AlertEntity;

public interface AlertJpaRepository extends JpaRepository<AlertEntity, Long> {}
