package com.hacof.communication.service;

import java.util.concurrent.CompletableFuture;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String content) throws MessagingException;

    CompletableFuture<Void> sendEmailAsync(String to, String subject, String content);
}
