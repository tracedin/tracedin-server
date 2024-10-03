package com.univ.tracedin.common.dto;

import java.util.HashMap;
import java.util.Map;

public record SearchCursor(Integer size, Map<String, Object> afterKey) {

    public SearchCursor {
        if (afterKey == null) {
            afterKey = new HashMap<>();
        }
    }
}
