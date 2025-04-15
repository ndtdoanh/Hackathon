package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.NotificationException;
import com.hacof.hackathon.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    @Override
    public void sendEmail(String to, String subject, String content) {
        validateEmailParameters(to, subject, content);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.debug("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new NotificationException("Failed to send email to " + to, e);
        }
    }

    @Async
    @Override
    public void sendBulkEmails(List<String> toList, String subject, String content) {
        validateEmailParameters(subject, content);

        List<String> validEmails = toList.stream()
                .filter(email -> email != null && !email.trim().isEmpty())
                .distinct()
                .toList();
        if (validEmails.isEmpty()) {
            log.warn("No valid email addresses provided for bulk send");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(fromEmail); // Required field
            helper.setBcc(validEmails.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("Bulk email sent to {} recipients", validEmails.size());
        } catch (Exception e) {
            log.error("Failed to send bulk email to {} recipients", validEmails.size(), e);
            throw new NotificationException("Failed to send bulk email to " + validEmails.size() + " recipients", e);
        }
    }

    private void validateEmailParameters(String subject, String content) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new InvalidInputException("Email subject cannot be empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new InvalidInputException("Email content cannot be empty");
        }
    }

    private void validateEmailParameters(String to, String subject, String content) {
        validateEmailParameters(subject, content);
        if (to == null || to.trim().isEmpty()) {
            throw new InvalidInputException("Email recipient cannot be empty");
        }
    }
}
