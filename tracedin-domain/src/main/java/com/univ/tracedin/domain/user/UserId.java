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
}
