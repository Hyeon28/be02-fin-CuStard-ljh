package com.example.qna.qna.repository;

import com.example.qna.qna.model.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna,Long> {
}
