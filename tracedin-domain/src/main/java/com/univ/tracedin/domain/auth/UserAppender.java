package com.univ.tracedin.domain.auth;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserProfile;
import com.univ.tracedin.domain.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    public void append(UserProfile profile, String password) {
        userRepository.save(User.create(profile, password));
    }
}
