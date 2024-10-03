package com.univ.tracedin.api.auth;

import org.springframework.http.ResponseEntity;

import com.univ.tracedin.api.auth.dto.LoginRequest;
import com.univ.tracedin.api.auth.dto.SignUpRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증 API")
public interface AuthApiDocs {

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    void signup(SignUpRequest request);

    @Operation(summary = "로그인", description = "로그인을 진행하고 로그인이 성공하면 토큰(Access, Refresh)을 반환합니다.")
    ResponseEntity<Void> login(LoginRequest request);
}
