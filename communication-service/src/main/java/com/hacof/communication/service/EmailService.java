package com.hacof.communication.service;

import jakarta.mail.MessagingException;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    void sendEmail(String to, String subject, String content) throws MessagingException;

    CompletableFuture<Void> sendEmailAsync(String to, String subject, String content);
}
