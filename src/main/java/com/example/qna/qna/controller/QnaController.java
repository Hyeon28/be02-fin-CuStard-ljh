package com.example.qna.qna.controller;

import com.example.qna.qna.model.request.PostQnaReadReq;
import com.example.qna.qna.model.request.PostQnaRegisterReq;
import com.example.qna.qna.model.response.GetQnaListRes;
import com.example.qna.qna.model.response.PostQnaReadRes;
import com.example.qna.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
@CrossOrigin("*")
public class QnaController {
    private final QnaService qnaService;



    @PostMapping("/register")
    public ResponseEntity<String> registerArticle(@RequestBody PostQnaRegisterReq postQnaRegisterReq) {
        qnaService.registerQna(postQnaRegisterReq);
        return ResponseEntity.ok("게시글 등록 완료");
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<PostQnaReadRes> readArticle(@PathVariable Long idx, @RequestBody PostQnaReadReq postQnaReadReq) {
        PostQnaReadRes postQnaReadRes = qnaService.readQna(idx, postQnaReadReq);
        return ResponseEntity.ok(postQnaReadRes);
    }


    @GetMapping("/list")
    public ResponseEntity<List<GetQnaListRes>> findAll() {
        List<GetQnaListRes> articles = qnaService.list();
        return ResponseEntity.ok(articles);
    }

}
