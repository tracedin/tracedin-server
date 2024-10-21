package com.univ.tracedin.infra.project.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.Node;
import com.univ.tracedin.domain.project.NodeType;
import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectOwner;
import com.univ.tracedin.domain.project.ProjectRepository;
import com.univ.tracedin.infra.project.entity.ProjectEntity;
import com.univ.tracedin.infra.project.exception.ProjectNotFoundException;
import com.univ.tracedin.infra.span.repository.SpanElasticSearchRepository;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final ProjectJpaRepository projectJpaRepository;
    private final SpanElasticSearchRepository spanElasticSearchRepository;

    @Override
    public Project save(Project project) {
        return projectJpaRepository.save(ProjectEntity.from(project)).toDomain();
    }

    @Override
    public Project findByKey(String projectKey) {
        return projectJpaRepository
                .findByProjectKey(projectKey)
                .map(ProjectEntity::toDomain)
                .orElseThrow(() -> ProjectNotFoundException.EXCEPTION);
    }

    @Override
    public List<Project> getByOwner(ProjectOwner owner) {
        return projectJpaRepository.findByOwnerId(owner.userId()).stream()
                .map(ProjectEntity::toDomain)
                .toList();
    }

    @Override
    public List<Node> findServiceNodeList(String projectKey) {
        List<String> serviceNames = spanElasticSearchRepository.findServiceNames(projectKey);
        return serviceNames.stream()
                .map(name -> Node.of(projectKey, name, NodeType.SERVICE))
                .toList();
    }
}
