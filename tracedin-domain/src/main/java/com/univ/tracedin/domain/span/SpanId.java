package com.univ.tracedin.domain.span;

import com.univ.tracedin.domain.global.BaseId;

public class SpanId extends BaseId<String> {

    public SpanId(String id) {
        super(id);
    }

    public static SpanId from(String id) {
        return new SpanId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpanId spanId)) {
            return false;
        }
        return getValue().equals(spanId.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
