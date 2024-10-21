package com.univ.tracedin.domain.alert;

public interface AlertClient {

    AlertType getAlertType();

    void sendAlert(String contact, Alert alert);
}
