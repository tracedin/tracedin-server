package com.univ.tracedin.api.auth.dto;

import com.univ.tracedin.domain.user.UserProfile;

public record SignUpRequest(String name, String email, String password) {

    public UserProfile toUserProfile() {
        return new UserProfile(name, email);
    }
}
