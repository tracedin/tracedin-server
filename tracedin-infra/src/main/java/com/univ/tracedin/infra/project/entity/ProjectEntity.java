package com.univ.tracedin.infra.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.project.Project;
import com.univ.tracedin.domain.project.ProjectId;
import com.univ.tracedin.domain.project.ProjectKey;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String projectKey;

    private Long ownerId;

    public static ProjectEntity from(Project project) {
        return ProjectEntity.builder()
                .name(project.getName())
                .description(project.getDescription())
                .projectKey(project.getProjectKey().value())
                .ownerId(project.getOwner().userId())
                .build();
    }

    public Project toDomain() {
        return Project.builder()
                .id(ProjectId.from(id))
                .name(name)
                .description(description)
                .projectKey(ProjectKey.from(projectKey))
                .build();
    }
}
