package com.hacof.identity.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hacof.identity.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
            helper.setSubject("Mã OTP xác thực email");

            String htmlContent =
                    "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>"
                            + "<h2 style='color: #333;'>Xác thực email</h2>"
                            + "<p>Mã OTP của bạn là: <strong style='font-size: 18px; letter-spacing: 2px;'>"
                            + otp + "</strong></p>" + "<p>Mã này sẽ hết hạn sau 5 phút.</p>"
                            + "<p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>"
                            + "<p>Trân trọng,<br/>Đội ngũ hỗ trợ</p>"
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
            helper.setSubject("Đặt lại mật khẩu");

            String htmlContent =
                    "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>"
                            + "<h2 style='color: #333;'>Đặt lại mật khẩu</h2>"
                            + "<p>Mã OTP để đặt lại mật khẩu của bạn là: <strong style='font-size: 18px; letter-spacing: 2px;'>"
                            + otp + "</strong></p>"
                            + "<p>Mã này sẽ hết hạn sau 5 phút.</p>"
                            + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>"
                            + "<p>Trân trọng,<br/>Đội ngũ hỗ trợ</p>"
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
