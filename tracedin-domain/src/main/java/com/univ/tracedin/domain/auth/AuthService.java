package com.univ.tracedin.domain.auth;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserAppender;
import com.univ.tracedin.domain.user.UserProfile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserAppender userAppender;
    private final UserAuthenticator userAuthenticator;

    public User signUp(UserProfile profile, String password) {
        return userAppender.append(profile, passwordEncoder.encode(password));
    }

    public Tokens login(LoginInfo login) {
        return userAuthenticator.authenticate(login);
    }
}
