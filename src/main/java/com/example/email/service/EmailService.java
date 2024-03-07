package com.example.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;



    public void sendEmail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setSubject("테스트 메일");
        helper.setTo("ininu@naver.com");
        Context context = new Context();
        context.setVariable("product1", "상품1");
        context.setVariable("name", "user01");


        String html = templateEngine.process("mail/mail", context);
        helper.setText(html, true);
        javaMailSender.send(message);
    }
}
