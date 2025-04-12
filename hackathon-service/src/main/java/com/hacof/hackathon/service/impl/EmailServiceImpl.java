package com.hacof.hackathon.service.impl;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // For individual emails
    @Async
    @Override
    public void sendEmail(String to, String subject, String content) {
        if (to == null || to.trim().isEmpty()) {
            log.error("Email address is null or empty");
            throw new InvalidInputException("Email address must not be null or empty");
        }

        try {
            log.debug("Preparing to send email to: {}", to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {} - Error: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    // New bulk email method using BCC
    @Async
    @Override
    public void sendBulkEmails(List<String> toList, String subject, String content) {
        if (toList == null || toList.isEmpty()) {
            log.warn("Email addresses list is null or empty");
            return;
        }

        // Filter valid emails
        List<String> validEmails = toList.stream()
                .filter(email -> email != null && !email.trim().isEmpty())
                .collect(Collectors.toList());

        if (validEmails.isEmpty()) {
            log.debug("No valid email addresses provided");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(fromEmail); // Required field, set to sender
            helper.setBcc(validEmails.toArray(new String[0])); // All recipients in BCC
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("Bulk email sent successfully to {} recipients", validEmails.size());
        } catch (Exception e) {
            log.error("Failed to send bulk email - Error: {}", e.getMessage());
            throw new RuntimeException("Failed to send bulk email: " + e.getMessage(), e);
        }
    }
}
