package com.univ.tracedin.infra.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.univ.tracedin.domain.user.User;
import com.univ.tracedin.domain.user.UserId;
import com.univ.tracedin.domain.user.UserRepository;
import com.univ.tracedin.infra.user.entity.UserEntity;
import com.univ.tracedin.infra.user.exception.UserNotFoundException;

@Repository
@Transactional
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public User findById(UserId id) {
        return userJpaRepository
                .findById(id.getValue())
                .map(UserEntity::toDomain)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User findByEmail(String email) {
        return userJpaRepository
                .findByEmail(email)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.deleteById(user.getId().getValue());
    }
}
