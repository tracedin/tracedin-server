package com.univ.tracedin.domain.alert;

import com.univ.tracedin.domain.global.BaseId;

public class AlertMethodId extends BaseId<Long> {

    public AlertMethodId(Long id) {
        super(id);
    }

    public static AlertMethodId from(Long id) {
        return new AlertMethodId(id);
    }
}
