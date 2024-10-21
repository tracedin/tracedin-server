package com.univ.tracedin.domain.alert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class AlertClientFactory {

    private final Map<AlertType, AlertClient> alertClients;

    public AlertClientFactory(List<AlertClient> alertClients) {
        this.alertClients =
                alertClients.stream()
                        .collect(Collectors.toMap(AlertClient::getAlertType, Function.identity()));
    }

    public AlertClient getClient(AlertType alertType) {
        return alertClients.get(alertType);
    }
}
