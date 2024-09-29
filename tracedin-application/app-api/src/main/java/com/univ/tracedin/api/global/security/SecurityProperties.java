package com.univ.tracedin.api.global.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProperties {

    public static String SECRET_KEY;
    public static int ACCESS_TOKEN_EXPIRATION;
    public static String ACCESS_TOKEN_HEADER = AUTHORIZATION;
    public static String REFRESH_TOKEN_HEADER;
    public static String BEARER = "Bearer ";

    @Value("${jwt.secret-key}")
    public void setSecretKey(String secretKey) {
        SecurityProperties.SECRET_KEY = secretKey;
    }

    @Value("${jwt.access-token.expiration}")
    public void setAccessTokenExpiration(int accessTokenExpiration) {
        SecurityProperties.ACCESS_TOKEN_EXPIRATION = accessTokenExpiration;
    }

    @Value("${jwt.refresh-token.header}")
    public void setRefreshToken(String refreshToken) {
        SecurityProperties.REFRESH_TOKEN_HEADER = refreshToken;
    }
}
