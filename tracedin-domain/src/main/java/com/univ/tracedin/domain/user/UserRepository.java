package com.univ.tracedin.domain.user;

public interface UserRepository {

    User save(User user);

    User findById(UserId id);

    User findByEmail(String email);

    void delete(User user);
}
