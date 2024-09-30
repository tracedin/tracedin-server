package com.univ.tracedin.domain.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    public void append(UserProfile profile, String password) {
        userRepository.save(User.create(profile, password));
    }
}
