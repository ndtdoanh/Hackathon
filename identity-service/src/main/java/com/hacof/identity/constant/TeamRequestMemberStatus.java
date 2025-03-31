package com.hacof.identity.constant;

public enum TeamRequestMemberStatus {
    PENDING, // waitting for approval
    APPROVED, // member was approved
    REJECTED, // member was rejected
    NO_RESPONSE // member did not response in long time
}
