package com.univ.tracedin.infra.alert.client;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.univ.tracedin.domain.alert.Alert;
import com.univ.tracedin.domain.alert.AlertClient;
import com.univ.tracedin.domain.alert.AlertType;

@Component
@RequiredArgsConstructor
public class EmailAlertClient implements AlertClient {

    @Override
    public void sendAlert(String contact, Alert alert) {}

    @Override
    public AlertType getAlertType() {
        return AlertType.EMAIL;
    }
}
