package com.univ.tracedin.domain.auth;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.UserProfile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserAppender userAppender;
    private final UserAuthenticator userAuthenticator;

    public void signUp(UserProfile profile, String password) {
        userAppender.append(profile, passwordEncoder.encode(password));
    }

    public Tokens login(LoginInfo login) {
        return userAuthenticator.authenticate(login);
    }
}
