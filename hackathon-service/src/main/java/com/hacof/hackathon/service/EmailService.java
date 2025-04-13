package com.hacof.hackathon.service;

import java.util.List;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void sendBulkEmails(List<String> toList, String subject, String content);
    void sendHtmlEmail(String to, String subject, String htmlContent);
    void sendBulkHtmlEmails(List<String> toList, String subject, String htmlContent);
}
