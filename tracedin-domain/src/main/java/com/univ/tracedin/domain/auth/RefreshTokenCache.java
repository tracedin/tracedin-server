package com.univ.tracedin.domain.auth;

import com.univ.tracedin.domain.user.UserId;

public interface RefreshTokenCache {

    void cache(RefreshToken refreshToken);

    RefreshToken get(UserId id);
}
