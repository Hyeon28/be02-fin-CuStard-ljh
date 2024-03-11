package com.example.recommend.controller;

import com.example.recommend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class RecommendController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final ViewService viewService;
    private final ProductService productService;
    private final RecommendService recommendService;


    @GetMapping("/customer")
    public void customerRegister(){
        customerService.addData();
    }

    @GetMapping("/order")
    public void orderRegister(){
        orderService.addData();
    }

    @GetMapping("/product")
    public void productRegister(){
        productService.addData();
    }

    @GetMapping("/view")
    public void viewRegister(){
        viewService.addData();
    }

    @GetMapping("/recommend")
    public void recommendRun(){
        recommendService.recommend();
    }


}
