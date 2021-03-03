package com.task.email.impl;


import com.task.email.MailSenderManager;
import com.task.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@PropertySource("classpath:email/email.properties")
public class EmailServiceImpl implements EmailService {

    private final MailSenderManager mailSenderManager;

    @Autowired
    public EmailServiceImpl(MailSenderManager mailSenderManager) {
        this.mailSenderManager = mailSenderManager;
    }


    @Override
    public void sendEmail(String to, String message, String text) {
        new Thread(() -> mailSenderManager.sendMail(to, text, message)).start();
    }
}
