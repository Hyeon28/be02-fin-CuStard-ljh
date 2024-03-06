package com.example.qna.answer.service;

import com.example.qna.answer.model.Answer;
import com.example.qna.answer.model.request.PostAnswerRegisterReq;
import com.example.qna.answer.repository.AnswerRepository;
import com.example.qna.common.BaseResponse;
import com.example.qna.qna.model.Qna;
import com.example.qna.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QnaRepository qnaRepository;

    public BaseResponse<Qna> registerAnswer(Long articleIdx, PostAnswerRegisterReq postAnswerRegisterReq) {
        if (postAnswerRegisterReq.getAnswerContent() != null) {
            Qna qna = null;
            Optional<Qna> result = qnaRepository.findById(articleIdx);
            if (result.isPresent()) {
                qna = result.get();
                answerRepository.save(Answer.builder().answerContent(postAnswerRegisterReq.getAnswerContent()).qna(qna).build());
            } else {
                return BaseResponse.failResponse(2306, "존재하지 않는 게시물 입니다.");
            }
            return BaseResponse.successResponse("1:1 문의 작성 성공", qna);
        } else {
            return BaseResponse.failResponse(400, "내용을 입력하지 않았습니다.");
        }
    }
}
