package com.univ.tracedin.api.auth.dto;

import com.univ.tracedin.domain.user.UserProfile;
import com.univ.tracedin.domain.user.UserRole;

public record SignUpRequest(String name, String email, UserRole userRole, String password) {

    public UserProfile toUserProfile() {
        return new UserProfile(name, email, userRole);
    }
}
