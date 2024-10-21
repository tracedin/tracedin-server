package com.univ.tracedin.domain.alert;

import com.univ.tracedin.domain.global.BaseId;

public class AlertId extends BaseId<Long> {

    public AlertId(Long id) {
        super(id);
    }

    public static AlertId from(Long id) {
        return new AlertId(id);
    }
}
