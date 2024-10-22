package com.univ.tracedin.domain.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    public User append(UserProfile profile, String password) {
        return userRepository.save(User.create(profile, password, UserRole.USER));
    }
}
