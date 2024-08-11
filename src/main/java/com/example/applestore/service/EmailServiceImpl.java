package com.example.applestore.service;
import com.example.applestore.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${email.recipient.address}")
    private String recipientAddress;

    @Value("${spring.mail.username}")
    private String senderAddress;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailFromUser(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientAddress);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}