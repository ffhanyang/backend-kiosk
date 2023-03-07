package com.ffhanyang.kiosk.model.member;

public enum ROLE {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), GUEST("ROLE_GUEST");

    private final String value;

    ROLE(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ROLE of(String name) {
        for (ROLE role : ROLE.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }
}
