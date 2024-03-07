package com.example.email.controller;

import com.example.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class emailController {
    private final EmailService emailService;
    //TODO : 이메일 배치 처리 필요, 추천 알고리즘을 통해 상품 가져와서 이메일 전송하는 html 코드에 적용 필요.
    @PostMapping("/test")
    public ResponseEntity testEmail() throws MessagingException {

        emailService.sendEmail();
        return ResponseEntity.ok().body("전송");
    }

}
