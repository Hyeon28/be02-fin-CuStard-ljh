package com.example.recommend.service;

import com.example.recommend.model.Product;
import com.example.recommend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addData() {
        List<Product> products = new ArrayList<>();

        products.add(Product.builder().name("아이폰 13").category("전자제품").build());
        products.add(Product.builder().name("갤럭시 S22").category("전자제품").build());
        products.add(Product.builder().name("맥북 프로").category("전자제품").build());
        products.add(Product.builder().name("아이패드 에어").category("전자제품").build());
        products.add(Product.builder().name("에어팟 프로").category("전자제품").build());
        products.add(Product.builder().name("갤럭시 버즈").category("전자제품").build());

        products.add(Product.builder().name("나이키 에어맥스").category("운동화").build());
        products.add(Product.builder().name("아디다스 울트라부스트").category("운동화").build());
        products.add(Product.builder().name("뉴발란스 990").category("운동화").build());
        products.add(Product.builder().name("언더아머 트리빅").category("운동화").build());
        products.add(Product.builder().name("푸마 썬더").category("운동화").build());
        products.add(Product.builder().name("리복 클럽 C").category("운동화").build());

        products.add(Product.builder().name("스타벅스 아메리카노").category("음료").build());
        products.add(Product.builder().name("카페라떼").category("음료").build());
        products.add(Product.builder().name("코카콜라").category("음료").build());
        products.add(Product.builder().name("오렌지 주스").category("음료").build());
        products.add(Product.builder().name("바나나 우유").category("음료").build());
        products.add(Product.builder().name("레몬에이드").category("음료").build());

        productRepository.saveAll(products);
    }
}
