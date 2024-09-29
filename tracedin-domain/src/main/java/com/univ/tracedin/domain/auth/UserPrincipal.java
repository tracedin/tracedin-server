package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.UserRole;

public record UserPrincipal(long userId, UserRole role) {

    public static UserPrincipal of(long userId, UserRole role) {
        return new UserPrincipal(userId, role);
    }
}
