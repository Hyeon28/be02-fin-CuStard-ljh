package com.example.adminqna.answer.controller;

import com.example.adminqna.answer.model.request.PostAnswerRegisterReq;
import com.example.adminqna.answer.model.response.PostAnswerRegisterRes;
import com.example.adminqna.answer.service.AnswerService;
import com.example.adminqna.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/article")
@CrossOrigin("*")
public class AnswerController {
    private final AnswerService answerService;


    @RequestMapping(method = RequestMethod.POST,value = "/answer/{idx}")
    public BaseResponse<PostAnswerRegisterRes> registerAnswer(@PathVariable Long idx, @RequestBody PostAnswerRegisterReq postAnswerRegisterReq){
        return answerService.registerAnswer(idx,postAnswerRegisterReq);
    }
}
