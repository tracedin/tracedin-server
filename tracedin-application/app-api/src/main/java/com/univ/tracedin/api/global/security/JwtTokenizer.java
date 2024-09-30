package com.univ.tracedin.api.global.security;

import static com.univ.tracedin.api.global.security.SecurityProperties.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.univ.tracedin.api.auth.exception.ExpiredTokenException;
import com.univ.tracedin.api.auth.exception.InvalidTokenException;
import com.univ.tracedin.domain.auth.TokenGenerator;
import com.univ.tracedin.domain.auth.Tokens;
import com.univ.tracedin.domain.auth.UserPrincipal;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenizer implements TokenGenerator {

    public static String generateAccessToken(UserPrincipal principal) {
        Key key = getKeyFromSecretKey(SECRET_KEY);

        return Jwts.builder()
                .setSubject(String.valueOf(principal.userId()))
                .claim("role", principal.role().name())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(UserPrincipal principal) {
        Key key = getKeyFromSecretKey(SECRET_KEY);

        return Jwts.builder()
                .setSubject(String.valueOf(principal.userId()))
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static void setInHeader(
            HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(ACCESS_TOKEN_HEADER, accessToken);
        response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
    }

    public static Key getKeyFromSecretKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Date getTokenExpiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, ACCESS_TOKEN_EXPIRATION);
        return calendar.getTime();
    }

    public static Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public static Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public static UserPrincipal extractPrincipal(String token) {
        try {
            // JWT 파싱 및 유효성 검사
            Claims claims =
                    Jwts.parserBuilder()
                            .setSigningKey(getKeyFromSecretKey(SECRET_KEY))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

            long userId = Long.parseLong(claims.getSubject());
            UserRole role = UserRole.valueOf(claims.get("role", String.class));
            return UserPrincipal.of(UserId.from(userId), role);

        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    @Override
    public Tokens generate(UserPrincipal principal) {
        return Tokens.of(generateAccessToken(principal), generateRefreshToken(principal));
    }
}
