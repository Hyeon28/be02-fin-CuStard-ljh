package com.example.qna.qna.service;

import com.example.qna.answer.model.Answer;
import com.example.qna.answer.repository.AnswerRepository;
import com.example.qna.common.BaseResponse;
import com.example.qna.qna.model.Qna;
import com.example.qna.qna.model.request.PostQnaReadReq;
import com.example.qna.qna.model.request.PostQnaRegisterReq;
import com.example.qna.qna.model.response.GetQnaListRes;
import com.example.qna.qna.model.response.PostQnaReadRes;
import com.example.qna.qna.model.response.PostQnaRegisterRes;
import com.example.qna.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<PostQnaRegisterRes> registerQna(PostQnaRegisterReq postQnaRegisterReq) {
        if (postQnaRegisterReq.getTitle() == null) {
            return BaseResponse.failResponse(400, "제목을 입력하지 않았습니다.");
        } else if (postQnaRegisterReq.getQnaContent() == null) {
            return BaseResponse.failResponse(400, "내용을 입력하지 않았습니다.");
        } else if (postQnaRegisterReq.getQnaPwd() == null) {
            return BaseResponse.failResponse(400, "비밀번호를 입력하지 않았습니다.");
        } else {
            Qna qna = Qna.builder()
                    .title(postQnaRegisterReq.getTitle())
                    .qnaPwd(passwordEncoder.encode(postQnaRegisterReq.getQnaPwd()))
                    .qnaContent(postQnaRegisterReq.getQnaContent())
                    .build();

            qnaRepository.save(qna);

            PostQnaRegisterRes postQnaRegisterRes = PostQnaRegisterRes.builder().build();
            return null;
        }
    }

    public List<GetQnaListRes> list() {
        List<Qna> resultQna = qnaRepository.findAll();
        List<GetQnaListRes> getQnaListRes = new ArrayList<>();

        for (Qna qna : resultQna) {
            GetQnaListRes article = new GetQnaListRes();
            article.setIdx(qna.getIdx());
            article.setTitle(qna.getTitle());
            Optional<Answer> resultAnswer = answerRepository.findByQnaIdx(article.getIdx());
            if (resultAnswer.isPresent()) {
                Answer answer = resultAnswer.get();
                article.setAnswerContent(answer.getAnswerContent());
            }
            getQnaListRes.add(article);
        }
        return getQnaListRes;
    }

    public PostQnaReadRes readQna(Long idx, PostQnaReadReq postQnaReadReq) {
        Optional<Qna> result = qnaRepository.findById(idx);
        if (result.isPresent()) {
            Qna qna = result.get();
            Boolean passwordCheck = passwordEncoder.matches(postQnaReadReq.getQnaPwd(), qna.getQnaPwd());

            // 비밀번호 일치 여부에 따라 처리
            if (passwordCheck) {
                PostQnaReadRes postQnaReadRes =
                        PostQnaReadRes.builder()
                                .title(qna.getTitle())
                                .qnaContent(qna.getQnaContent())
                                .build();

                return postQnaReadRes;
            } else {
                //비밀번호 일치하지 않음 처리
            }
        } else {
            return null;
            //존재하지 않는 게시글 번호
        }
        return null;
    }
}

