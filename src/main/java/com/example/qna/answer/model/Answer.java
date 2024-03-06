package com.example.qna.answer.model;


import com.example.qna.qna.model.Qna;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String answerContent;

    @OneToOne
    @JoinColumn(name = "articleIdx")
    private Qna qna;
}
