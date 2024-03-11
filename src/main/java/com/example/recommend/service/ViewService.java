package com.example.recommend.service;

import com.example.recommend.model.Customer;
import com.example.recommend.model.Orders;
import com.example.recommend.model.Product;
import com.example.recommend.model.View;
import com.example.recommend.repository.CustomerRepository;
import com.example.recommend.repository.OrdersRepository;
import com.example.recommend.repository.ProductRepository;
import com.example.recommend.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ViewRepository viewRepository;
    private final Random random = new Random();

    public void addData(){
        List<Product> products = productRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        int numProducts = random.nextInt(6) + 3;

        for (int i = 0; i < customers.size(); i++) {
            Set<Integer> chosenProductIndices = new HashSet<>(); // Set to store chosen product indices

            for (int j = 0; j < numProducts; j++) {
                int productIndex;
                do {
                    productIndex = random.nextInt(products.size()); // Randomly select product index
                } while (!chosenProductIndices.add(productIndex)); // Ensure not already chosen

                Product product = products.get(productIndex);
                Customer customer = customers.get(i);

                View view = View.builder()
                        .product(product)
                        .customer(customer)
                        .build();

                viewRepository.save(view);
            }
        }
    }
}
