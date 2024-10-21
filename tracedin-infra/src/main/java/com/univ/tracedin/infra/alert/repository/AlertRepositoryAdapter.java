package com.univ.tracedin.infra.alert.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.alert.Alert;
import com.univ.tracedin.domain.alert.AlertMethod;
import com.univ.tracedin.domain.alert.AlertMethodId;
import com.univ.tracedin.domain.alert.AlertMethodRepository;
import com.univ.tracedin.domain.alert.AlertRepository;
import com.univ.tracedin.domain.project.ProjectId;
import com.univ.tracedin.infra.alert.entity.AlertEntity;
import com.univ.tracedin.infra.alert.entity.AlertMethodEntity;
import com.univ.tracedin.infra.alert.exception.AlertMethodNotFoundException;

@Repository
@Transactional
@RequiredArgsConstructor
public class AlertRepositoryAdapter implements AlertRepository, AlertMethodRepository {

    private final AlertJpaRepository alertJpaRepository;
    private final AlertMethodJpaRepository alertMethodJpaRepository;

    @Override
    public void save(Alert alert) {
        alertJpaRepository.save(AlertEntity.from(alert));
    }

    @Override
    public void delete(Alert alert) {
        alertJpaRepository.delete(AlertEntity.from(alert));
    }

    @Override
    public void saveMethod(AlertMethod alertMethod) {
        alertMethodJpaRepository.save(AlertMethodEntity.from(alertMethod));
    }

    @Override
    public void deleteMethod(AlertMethod alertMethod) {
        alertMethodJpaRepository.delete(AlertMethodEntity.from(alertMethod));
    }

    @Override
    public AlertMethod findMethodById(AlertMethodId alertMethodId) {
        return alertMethodJpaRepository
                .findById(alertMethodId.getValue())
                .map(AlertMethodEntity::toDomain)
                .orElseThrow(() -> AlertMethodNotFoundException.EXCEPTION);
    }

    @Override
    public List<AlertMethod> findAllMethodByProjectId(ProjectId projectId) {
        return alertMethodJpaRepository.findAllByProjectId(projectId.getValue()).stream()
                .map(AlertMethodEntity::toDomain)
                .toList();
    }
}
