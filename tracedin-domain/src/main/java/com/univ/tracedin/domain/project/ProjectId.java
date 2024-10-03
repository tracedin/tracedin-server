package com.univ.tracedin.domain.project;

import com.univ.tracedin.domain.global.BaseId;

public class ProjectId extends BaseId<Long> {

    public ProjectId(Long id) {
        super(id);
    }

    public static ProjectId from(Long id) {
        return new ProjectId(id);
    }
}
