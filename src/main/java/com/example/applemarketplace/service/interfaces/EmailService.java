package com.example.applemarketplace.service.interfaces;

public interface EmailService {
    void sendEmailFromUser(String subject, String text);

    void sendEmail(String toEmail, String subject, String text);
}
