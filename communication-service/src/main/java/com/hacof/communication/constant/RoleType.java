package com.hacof.communication.constant;

import java.util.Arrays;

public enum RoleType {
    ADMIN,
    ORGANIZER,
    JUDGE,
    MENTOR,
    GUEST,
    TEAM_MEMBER,
    TEAM_LEADER;

    public static RoleType fromString(String name) {
        return Arrays.stream(RoleType.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role type: " + name));
    }
}
