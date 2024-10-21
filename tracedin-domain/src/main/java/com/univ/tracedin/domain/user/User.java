package com.univ.tracedin.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.univ.tracedin.domain.auth.UserPrincipal;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private UserId id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

    private UserRole role;

    public static User create(UserProfile userProfile, String password) {
        return User.builder()
                .name(userProfile.name())
                .email(userProfile.email())
                .password(password)
                .role(userProfile.role())
                .build();
    }

    public UserPrincipal getPrincipal() {
        return UserPrincipal.of(id, role);
    }
}
