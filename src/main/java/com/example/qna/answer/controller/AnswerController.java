package com.example.qna.answer.controller;

import com.example.qna.answer.model.request.PostAnswerRegisterReq;
import com.example.qna.answer.service.AnswerService;
import com.example.qna.common.BaseResponse;
import com.example.qna.qna.model.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/article")
@CrossOrigin("*")
public class AnswerController {
    private final AnswerService answerService;


    @RequestMapping(method = RequestMethod.POST,value = "/answer/{id}")
    public BaseResponse<Qna> registerAnswer(@PathVariable Long idx, @RequestBody PostAnswerRegisterReq postAnswerRegisterReq){
        return answerService.registerAnswer(idx,postAnswerRegisterReq);
    }
}
