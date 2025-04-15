package com.hacof.identity.service.impl;

import com.hacof.identity.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final StringRedisTemplate redisTemplate;
    private static final int OTP_EXPIRATION = 5;

    @Override
    public String generateOtp(String email) {
        try {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            redisTemplate.opsForValue().set(email, otp, OTP_EXPIRATION, TimeUnit.MINUTES);
            return otp;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo OTP: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        return storedOtp != null && storedOtp.equals(inputOtp);
    }

    @Override
    public void removeOtp(String key) {
        redisTemplate.delete(key);
    }
}
