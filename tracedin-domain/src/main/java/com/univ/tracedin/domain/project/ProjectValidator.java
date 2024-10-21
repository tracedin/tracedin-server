package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.user.User;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    public void validate(Project project, User user) {}
}
