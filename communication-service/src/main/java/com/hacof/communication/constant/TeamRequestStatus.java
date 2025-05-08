package com.hacof.communication.constant;

public enum TeamRequestStatus {
    PENDING, // waitting for approval
    UNDER_REVIEW, // UNDER_REVIEW, // all member was responsed, waitting for admin to approve
    APPROVED, // team request was approved
    REJECTED, // team request was rejected
    CANCELED // leader canceled the request
}
