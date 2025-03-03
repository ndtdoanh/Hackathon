package com.hacof.identity.services;

public interface OtpService {
    String generateOtp(String email);

    boolean verifyOtp(String email, String inputOtp);
}
