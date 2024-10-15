package com.univ.tracedin.domain.project;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.global.BaseId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectId extends BaseId<Long> {

    public ProjectId(Long id) {
        super(id);
    }

    public static ProjectId from(Long id) {
        return new ProjectId(id);
    }
}
