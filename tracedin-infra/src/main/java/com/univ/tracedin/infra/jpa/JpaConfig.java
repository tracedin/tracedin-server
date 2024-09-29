package com.univ.tracedin.infra.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.univ.tracedin.infra")
@EnableJpaAuditing
public class JpaConfig {}
