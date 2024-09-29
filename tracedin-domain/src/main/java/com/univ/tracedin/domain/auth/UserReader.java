package com.univ.tracedin.domain.auth;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User read(UserId id) {
        return userRepository.findById(id);
    }

    public User read(String email) {
        return userRepository.findByEmail(email);
    }
}
