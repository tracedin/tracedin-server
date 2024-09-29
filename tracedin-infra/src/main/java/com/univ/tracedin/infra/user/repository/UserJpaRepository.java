package com.univ.tracedin.infra.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univ.tracedin.infra.user.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
