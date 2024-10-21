package com.univ.tracedin.domain.project;

import com.univ.tracedin.domain.global.BaseId;

public class ProjectMemberId extends BaseId<Long> {

    public ProjectMemberId(Long id) {
        super(id);
    }

    public static ProjectMemberId from(Long id) {
        return new ProjectMemberId(id);
    }
}
