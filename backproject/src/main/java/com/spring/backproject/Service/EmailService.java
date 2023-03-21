package com.spring.backproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String email,String subject,String body)
    {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("masghouniamal84@gmail.com");
        message.setSubject(subject);
        message.setTo(email);
        message.setText(body);
        javaMailSender.send(message);
System.out.println("mail envoye");
    }
}
