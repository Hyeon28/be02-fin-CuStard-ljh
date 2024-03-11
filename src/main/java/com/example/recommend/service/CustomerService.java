package com.example.recommend.service;

import com.example.recommend.model.Customer;
import com.example.recommend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    public void addData() {

        for (int i = 1; i < 11; i++) {
            Customer user = Customer.builder().name("user"+i).build();
            customerRepository.save(user);
        }
    }
}
