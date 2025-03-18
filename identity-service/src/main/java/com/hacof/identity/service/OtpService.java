package com.hacof.identity.service;

public interface OtpService {
    String generateOtp(String email);

    boolean verifyOtp(String email, String inputOtp);

    void removeOtp(String key);
}
