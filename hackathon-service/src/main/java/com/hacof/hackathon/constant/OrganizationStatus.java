package com.hacof.hackathon.constant;

public enum OrganizationStatus {
    FPTU("FPTU"),
    NASA("NASA"),
    IAI_HACKATHON("IAI HACKATHON"),
    CE_HACKATHON("CE Hackathon"),
    OTHERS("Others");

    private final String displayName;

    OrganizationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}