package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.User;

public record Tokens(String accessToken, String refreshToken) {

    public static Tokens of(String accessToken, String refreshToken) {
        return new Tokens(accessToken, refreshToken);
    }

    public RefreshToken getRefreshToken(User user) {
        return new RefreshToken(user.getId().getValue(), refreshToken);
    }
}
