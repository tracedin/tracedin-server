package com.univ.tracedin.infra.user.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.univ.tracedin.domain.auth.RefreshToken;
import com.univ.tracedin.domain.auth.RefreshTokenCache;
import com.univ.tracedin.domain.user.UserId;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCacheAdapter implements RefreshTokenCache {

    private final RedisTemplate<String, RefreshToken> template;

    @Override
    public void cache(RefreshToken refreshToken) {
        String key = getKey(refreshToken.userId());
        log.info("Set Refresh Token from {} : {}", key, refreshToken);
        template.opsForValue().set(key, refreshToken);
    }

    @Override
    public RefreshToken get(UserId id) {
        String key = getKey(id.getValue());
        log.info("Get Refresh Token from {}", key);
        return template.opsForValue().get(getKey(id.getValue()));
    }

    private String getKey(long userId) {
        return "USER_REFRESH_TOKEN:" + userId;
    }
}
