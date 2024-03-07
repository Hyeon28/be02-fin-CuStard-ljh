package com.example.adminqna.qna.repository;

import com.example.adminqna.qna.model.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna,Long> {
}
