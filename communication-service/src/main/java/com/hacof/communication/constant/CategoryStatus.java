package com.hacof.communication.constant;

public enum CategoryStatus {
    CODING("Coding Hackathons"),
    EXTERNAL("External Hackathons"),
    INTERNAL("Internal Hackathons"),
    DESIGN("Design Hackathons"),
    OTHERS("Others");

    private final String displayName;

    CategoryStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
