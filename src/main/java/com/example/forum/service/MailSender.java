package com.example.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// mailSender - ЧТО отправлять
@Service
public class MailSender {

    // JavaMailSender - объект в MAILCONFIG - там же и настраивается полностью
    // КУДА отправлять
    @Autowired
    private JavaMailSender mailSender;

    // ЯВНОЕ УКАЗАНИЕ ОТПРАВИТЕЛЯ
    @Value("${spring.mail.username}")
    private String username;


    public void send(String emailTo, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}