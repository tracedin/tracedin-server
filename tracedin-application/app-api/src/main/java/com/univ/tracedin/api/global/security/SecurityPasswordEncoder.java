package com.univ.tracedin.api.global.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.auth.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class SecurityPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encode(String plainPassword) {
        return bCryptPasswordEncoder.encode(plainPassword);
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}
