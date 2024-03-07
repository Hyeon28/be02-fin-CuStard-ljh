package com.example.adminqna.answer.service;

import com.example.adminqna.answer.model.Answer;
import com.example.adminqna.answer.model.request.PostAnswerRegisterReq;
import com.example.adminqna.answer.model.response.PostAnswerRegisterRes;
import com.example.adminqna.answer.repository.AnswerRepository;
import com.example.adminqna.common.BaseResponse;
import com.example.adminqna.qna.model.Qna;
import com.example.adminqna.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QnaRepository qnaRepository;

    public BaseResponse<PostAnswerRegisterRes> registerAnswer(Long qnaIdx, PostAnswerRegisterReq postAnswerRegisterReq) {
        Optional<Answer> searchdup = answerRepository.findByQnaIdx(qnaIdx); //이미 작성된 답변이 있을 경우
        if(searchdup.isEmpty()){
            if (postAnswerRegisterReq.getAnswerContent() != null) {
                PostAnswerRegisterRes postAnswerRegisterRes = null;
                Optional<Qna> result = qnaRepository.findById(qnaIdx);
                if (result.isPresent()) {
                    Qna qna = result.get();
                    Answer answer = Answer.builder()
                            .answerContent(postAnswerRegisterReq
                                    .getAnswerContent())
                            .qna(qna)
                            .build();
                    answerRepository.save(answer);

                    postAnswerRegisterRes = PostAnswerRegisterRes.builder()
                            .title(qna.getTitle())
                            .qnaContent(qna.getQnaContent())
                            .answerContent(answer.getAnswerContent())
                            .build();
                } else {
                    return BaseResponse.failResponse(2306, "존재하지 않는 게시물 입니다.");
                }
                return BaseResponse.successResponse("1:1 문의 작성 성공", postAnswerRegisterRes);
            } else {
                return BaseResponse.failResponse(400, "내용을 입력하지 않았습니다.");
            }
        }
        else {
            return BaseResponse.failResponse(400, "이미 작성한 답변이 존재합니다.");
        }
    }
}
