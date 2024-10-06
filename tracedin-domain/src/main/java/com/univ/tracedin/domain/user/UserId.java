package com.univ.tracedin.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.univ.tracedin.domain.global.BaseId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId extends BaseId<Long> {

    public UserId(Long id) {
        super(id);
    }

    public static UserId from(Long id) {
        return new UserId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserId userId)) {
            return false;
        }
        return getValue().equals(userId.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
