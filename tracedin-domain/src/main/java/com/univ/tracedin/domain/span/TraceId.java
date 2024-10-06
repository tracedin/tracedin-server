package com.univ.tracedin.domain.span;

import com.univ.tracedin.domain.global.BaseId;

public class TraceId extends BaseId<String> {

    public TraceId(String id) {
        super(id);
    }

    public static TraceId from(String id) {
        return new TraceId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraceId traceId)) {
            return false;
        }
        return getValue().equals(traceId.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
