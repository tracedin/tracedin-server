package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.UserId;

public record RefreshToken(UserId userId, String value) {

    public static RefreshToken of(UserId userId, String refreshToken) {
        return new RefreshToken(userId, refreshToken);
    }

    public boolean notEquals(String givenRefreshToken) {
        return value.equals(givenRefreshToken);
    }
}
