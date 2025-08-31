package com.staj.biletbul.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


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
