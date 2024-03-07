package com.example.adminqna.qna.service;

import com.example.adminqna.answer.model.Answer;
import com.example.adminqna.answer.repository.AnswerRepository;
import com.example.adminqna.qna.model.Qna;
import com.example.adminqna.qna.model.request.PostQnaReadReq;
import com.example.adminqna.qna.model.response.GetQnaListRes;
import com.example.adminqna.qna.model.response.PostQnaReadRes;
import com.example.adminqna.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final AnswerRepository answerRepository;

    public List<GetQnaListRes> list() {
        List<Qna> resultQna = qnaRepository.findAll();
        List<GetQnaListRes> getQnaListRes = new ArrayList<>();

        for (Qna qna : resultQna) {
            GetQnaListRes qnaListRes = new GetQnaListRes();
            qnaListRes.setIdx(qna.getIdx());
            qnaListRes.setTitle(qna.getTitle());
            Optional<Answer> resultAnswer = answerRepository.findByQnaIdx(qnaListRes.getIdx());
            if (resultAnswer.isPresent()) {
                Answer answer = resultAnswer.get();
                qnaListRes.setAnswerContent(answer.getAnswerContent());
            }
            getQnaListRes.add(qnaListRes);
        }
        return getQnaListRes;
    }

    public PostQnaReadRes readQna(Long idx) {
        Optional<Qna> result = qnaRepository.findById(idx);
        if (result.isPresent()) {
            Qna qna = result.get();

            PostQnaReadRes postQnaReadRes =
                    PostQnaReadRes.builder()
                            .title(qna.getTitle())
                            .qnaContent(qna.getQnaContent())
                            .build();

            return postQnaReadRes;
        }else{
            return null;
        }
    }
}

