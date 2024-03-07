package com.example.adminqna.answer.repository;

import com.example.adminqna.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQnaIdx(Long qnaIdx);
}
