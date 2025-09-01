package com.staj.biletbul.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendSimpleMail(String toMail,
                               String subject,
                               String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("muhammedduzgun00@gmail.com");
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
