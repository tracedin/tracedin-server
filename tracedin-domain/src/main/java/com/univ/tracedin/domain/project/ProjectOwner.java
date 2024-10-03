package com.univ.tracedin.domain.project;

import com.univ.tracedin.domain.user.User;

public record ProjectOwner(Long userId) {

    public static ProjectOwner from(User user) {
        return new ProjectOwner(user.getId().getValue());
    }
}
