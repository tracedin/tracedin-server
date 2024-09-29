package com.univ.tracedin.domain.auth;

public interface PasswordEncoder {

    String encode(String password);

    boolean matches(String password, String encodedPassword);
}
