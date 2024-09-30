package com.univ.tracedin.api.auth.dto;

import com.univ.tracedin.domain.auth.LoginInfo;

public record LoginRequest(String email, String password) {

    public LoginInfo toLoginInfo() {
        return new LoginInfo(email, password);
    }
}
