package com.univ.tracedin.domain.alert;

public interface AlertRepository {

    void save(Alert alert);

    void delete(Alert alert);
}
