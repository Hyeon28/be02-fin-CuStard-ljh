package com.example.adminqna.qna.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostQnaReadRes {
    private String title; //제목
    private String qnaContent; //본문
}
