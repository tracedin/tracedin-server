package com.univ.tracedin.domain.alert;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlertManager {

    private final AlertRepository alertRepository;

    public void append(Alert alert) {
        alertRepository.save(alert);
    }
}
