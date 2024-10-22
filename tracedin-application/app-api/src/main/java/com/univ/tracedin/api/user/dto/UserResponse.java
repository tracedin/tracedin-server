package com.univ.tracedin.api.user.dto;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserRole;

public record UserResponse(Long id, String name, String email, UserRole role) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId().getValue(), user.getName(), user.getEmail(), user.getRole());
    }
}
