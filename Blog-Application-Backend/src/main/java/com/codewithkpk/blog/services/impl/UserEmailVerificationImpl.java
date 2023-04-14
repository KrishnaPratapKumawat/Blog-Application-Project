package com.codewithkpk.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class UserEmailVerificationImpl {
    @Autowired
    private  JavaMailSender javaMailSender;

    @Autowired
    public UserEmailVerificationImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }
}
