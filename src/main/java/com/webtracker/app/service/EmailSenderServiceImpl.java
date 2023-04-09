package com.webtracker.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String email, String subject, String text) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("webtrackerzzpj@op.pl");
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }
}
