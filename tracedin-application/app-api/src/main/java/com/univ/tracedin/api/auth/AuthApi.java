package com.univ.tracedin.api.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.api.auth.dto.LoginRequest;
import com.univ.tracedin.api.auth.dto.SignUpRequest;
import com.univ.tracedin.api.global.util.TokenUtils;
import com.univ.tracedin.domain.auth.AuthService;
import com.univ.tracedin.domain.auth.Tokens;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthApi implements AuthApiDocs {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void signup(@RequestBody SignUpRequest request) {
        authService.signUp(request.toUserProfile(), request.password());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        Tokens tokens = authService.login(request.toLoginInfo());
        HttpHeaders tokenHeaders = TokenUtils.createTokenHeaders(tokens);
        return ResponseEntity.ok().headers(tokenHeaders).build();
    }
}
