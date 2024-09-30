package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserRole;

public record UserPrincipal(UserId userId, UserRole role) {

    public static UserPrincipal of(UserId userId, UserRole role) {
        return new UserPrincipal(userId, role);
    }
}
