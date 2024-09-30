package com.univ.tracedin.domain.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

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
