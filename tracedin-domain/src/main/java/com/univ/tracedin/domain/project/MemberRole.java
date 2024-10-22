package com.univ.tracedin.domain.project;

public enum MemberRole {
    ADMIN,
    MEMBER;

    public boolean isHigherThan(MemberRole role) {
        return this.ordinal() < role.ordinal();
    }
}
