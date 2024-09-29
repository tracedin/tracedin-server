package com.univ.tracedin.domain.auth;

public interface TokenGenerator {

    Tokens generate(UserPrincipal principal);
}
