package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.UserId;

public record RefreshToken(long userId, String refreshToken) {

    public static RefreshToken of(UserId userId, String refreshToken) {
        return new RefreshToken(userId.getValue(), refreshToken);
    }
}
