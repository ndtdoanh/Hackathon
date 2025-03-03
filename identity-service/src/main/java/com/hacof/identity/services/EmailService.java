package com.hacof.identity.services;

public interface EmailService {
    void sendOtp(String to, String otp);
}
