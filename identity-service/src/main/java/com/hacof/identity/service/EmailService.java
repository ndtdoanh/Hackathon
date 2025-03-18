package com.hacof.identity.service;

public interface EmailService {
    void sendOtp(String to, String otp);

    void sendPasswordResetOtp(String to, String otp);
}
