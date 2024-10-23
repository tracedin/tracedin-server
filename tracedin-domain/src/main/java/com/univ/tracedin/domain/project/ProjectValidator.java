package com.univ.tracedin.domain.project;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.project.exception.InvalidProjectKeyException;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectReader projectReader;

    public void validate(ProjectKey projectKey) {
        if (!projectReader.exists(projectKey)) {
            throw InvalidProjectKeyException.EXCEPTION;
        }
    }
}
