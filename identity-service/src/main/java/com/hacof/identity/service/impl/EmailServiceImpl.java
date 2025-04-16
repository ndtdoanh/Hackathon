package com.hacof.identity.service.impl;

import com.hacof.identity.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Email authentication OTP code");

            String htmlContent =
                    "<div style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; max-width: 600px; margin: 40px auto; border: 1px solid #dcdcdc; border-radius: 8px; overflow: hidden; box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);'>"
                            + "<div style='background-color: #2f4050; padding: 20px 30px; text-align: center;'>"
                            + "<h1 style='color: #ffffff; font-size: 24px; margin: 0;'>HACOF</h1>"
                            + "</div>"

                            + "<div style='background-color: #ffffff; padding: 30px;'>"
                            + "<p style='font-size: 16px; color: #333;'>Hello,</p>"
                            + "<p style='font-size: 16px; color: #333;'>You (or someone else) have requested an OTP code to verify your email. Here is your code:</p>"

                            + "<div style='text-align: center; margin: 30px 0;'>"
                            + "<span style='display: inline-block; font-size: 26px; color: #e74c3c; font-weight: bold; letter-spacing: 4px; padding: 12px 24px; border: 2px dashed #e74c3c; border-radius: 6px;'>"
                            + otp + "</span>"
                            + "</div>"

                            + "<p style='font-size: 14px; color: #555;'>This code will expire in <strong>5 minutes</strong>. If you did not request this, please ignore this email or contact support if you're concerned.</p>"

                            + "<p style='font-size: 14px; color: #999; margin-top: 30px;'>Best regards,</p>"
                            + "<p style='font-size: 14px; color: #2f4050; font-weight: bold;'>HACOF Support Team</p>"
                            + "</div>"

                            + "<div style='background-color: #f4f4f4; padding: 15px 30px; text-align: center;'>"
                            + "<p style='font-size: 12px; color: #999; margin: 0;'>This is an automated email. Please do not reply.</p>"
                            + "</div>"
                            + "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Đã gửi OTP thành công đến email: {}", to);
        } catch (MessagingException e) {
            log.error("Lỗi khi gửi email: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetOtp(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Password Reset");

            String htmlContent =
                    "<div style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; max-width: 600px; margin: 40px auto; border: 1px solid #dcdcdc; border-radius: 8px; overflow: hidden; box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);'>"
                            + "<div style='background-color: #2f4050; padding: 20px 30px; text-align: center;'>"
                            + "<h1 style='color: #ffffff; font-size: 24px; margin: 0;'>HACOF</h1>"
                            + "</div>"

                            + "<div style='background-color: #ffffff; padding: 30px;'>"
                            + "<h2 style='color: #333; font-size: 20px; margin-bottom: 20px;'>Password Reset Request</h2>"
                            + "<p style='font-size: 16px; color: #333;'>Hello,</p>"
                            + "<p style='font-size: 16px; color: #333;'>You (or someone else) have requested a password reset OTP code. Here is your code:</p>"

                            + "<div style='text-align: center; margin: 30px 0;'>"
                            + "<span style='display: inline-block; font-size: 26px; color: #e74c3c; font-weight: bold; letter-spacing: 4px; padding: 12px 24px; border: 2px dashed #e74c3c; border-radius: 6px;'>"
                            + otp + "</span>"
                            + "</div>"

                            + "<p style='font-size: 14px; color: #555;'>This code will expire in <strong>5 minutes</strong>. If you did not request a password reset, please ignore this email.</p>"

                            + "<p style='font-size: 14px; color: #999; margin-top: 30px;'>Best regards,</p>"
                            + "<p style='font-size: 14px; color: #2f4050; font-weight: bold;'>HACOF Support Team</p>"
                            + "</div>"

                            + "<div style='background-color: #f4f4f4; padding: 15px 30px; text-align: center;'>"
                            + "<p style='font-size: 12px; color: #999; margin: 0;'>This is an automated email. Please do not reply.</p>"
                            + "</div>"
                            + "</div>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Đã gửi OTP đặt lại mật khẩu thành công đến email: {}", to);
        } catch (MessagingException e) {
            log.error("Lỗi khi gửi email đặt lại mật khẩu: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi khi gửi email đặt lại mật khẩu: " + e.getMessage());
        }
    }
}
