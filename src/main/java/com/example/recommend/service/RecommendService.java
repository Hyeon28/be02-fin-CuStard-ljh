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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final ViewRepository viewRepository;
    private final CustomerRepository customerRepository;

    public void recommend() {
        // 아이템 간의 유사도를 계산하기 위한 아이템 특성 정보
        Map<String, Map<String, Double>> itemFeatures = new HashMap<>();
        // 예시 아이템의 특성 정보 추가
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            Map<String, Double> itemFeaturesMap = new HashMap<>();
            itemFeaturesMap.put(product.getCategory(), 1.0);
            itemFeaturesMap.put(product.getName(), 1.0);
            itemFeatures.put(product.getName(), itemFeaturesMap);
        }

        // 아이템 간의 유사도 계산
        Map<String, Map<String, Double>> similarityMatrix = calculateItemSimilarity(itemFeatures);

        // 테스트 사용자와 그 사용자의 조회 및 구매 내역
        Map<Customer, List<Product>> viewHistory = new HashMap<>();
        List<View> views = viewRepository.findAll();
        for (View view : views){
            Customer customer = view.getCustomer();
            Product product = view.getProduct();
            if (!viewHistory.containsKey(customer)) {
                viewHistory.put(customer, new ArrayList<>());
            }
            viewHistory.get(customer).add(product);
        }

        Map<Customer, List<Product>> purchaseHistory = new HashMap<>();
        List<Orders> orders = ordersRepository.findAll();
        for (Orders order : orders){
            Customer customer = order.getCustomer();
            Product product = order.getProduct();
            if (!purchaseHistory.containsKey(customer)) {
                purchaseHistory.put(customer, new ArrayList<>());
            }
            purchaseHistory.get(customer).add(product);
        }

        // 사용자들에게 추천될 아이템과 점수 계산
        Map<Customer, Map<Product, Double>> recommendationScores = recommendItems(similarityMatrix, viewHistory, purchaseHistory);

        // 각 사용자에게 추천되는 아이템 및 유사도 출력
        for (Map.Entry<Customer, Map<Product, Double>> entry : recommendationScores.entrySet()) {
            Customer customer = entry.getKey();
            List<Product> purchasedItems = purchaseHistory.getOrDefault(customer, new ArrayList<>());
            Map<Product, Double> recommendedItemsWithScore = entry.getValue();

            System.out.print(customer.getName() + "이 구매한 아이템: ");
            if (!purchasedItems.isEmpty()) {
                for (Product item : purchasedItems) {
                    System.out.print(item.getName() + ", ");
                }
            } else {
                System.out.print("없음");
            }
            System.out.println();

            System.out.println(customer.getName() + "에게 추천되는 아이템:");
            for (Map.Entry<Product, Double> recommendationEntry : recommendedItemsWithScore.entrySet()) {
                Product recommendedItem = recommendationEntry.getKey();
                Double similarityScore = recommendationEntry.getValue();
                System.out.println("- " + recommendedItem.getName() + " (유사도: " + similarityScore + ")");
            }
            System.out.println();
        }
    }

    public Map<String, Map<String, Double>> calculateItemSimilarity(Map<String, Map<String, Double>> itemFeatures) {
        Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry1 : itemFeatures.entrySet()) {
            String item1 = entry1.getKey();
            Map<String, Double> features1 = entry1.getValue();

            // 아이템1과 다른 모든 아이템 간의 유사도 계산
            Map<String, Double> similarities = new HashMap<>();
            for (Map.Entry<String, Map<String, Double>> entry2 : itemFeatures.entrySet()) {
                String item2 = entry2.getKey();
                if (!item1.equals(item2)) {
                    Map<String, Double> features2 = entry2.getValue();
                    double similarity = calculateJaccardSimilarity(features1.keySet(), features2.keySet());
                    similarities.put(item2, similarity);
                }
            }
            similarityMatrix.put(item1, similarities);
        }

        return similarityMatrix;
    }

    // 자카드 유사도 계산
    private double calculateJaccardSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        if (union.size() == 0) {
            return 0.0;
        } else {
            return (double) intersection.size() / union.size();
        }
    }

    // 코사인 유사도 계산

    public Map<Customer, Map<Product, Double>> recommendItems(Map<String, Map<String, Double>> similarityMatrix, Map<Customer, List<Product>> viewHistory, Map<Customer, List<Product>> purchaseHistory) {
        Map<Customer, Map<Product, Double>> recommendationScores = new HashMap<>();

        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            Set<Product> viewedItems = new HashSet<>(viewHistory.getOrDefault(customer, Collections.emptyList()));
            Set<Product> purchasedItems = new HashSet<>(purchaseHistory.getOrDefault(customer, Collections.emptyList()));

            // Collect similar items purchased by similar users
            Map<Product, Double> recommendationScoresMap = new HashMap<>();
            for (Customer otherCustomer : customers) {
                if (!otherCustomer.equals(customer)) {
                    List<Product> otherPurchasedItems = purchaseHistory.getOrDefault(otherCustomer, Collections.emptyList());
                    for (Product otherPurchasedItem : otherPurchasedItems) {
                        double similarity = calculateItemSimilarity(similarityMatrix, otherPurchasedItem.getName(), viewedItems);
                        if (similarity > 0 && !purchasedItems.contains(otherPurchasedItem)) {
                            recommendationScoresMap.put(otherPurchasedItem, recommendationScoresMap.getOrDefault(otherPurchasedItem, 0.0) + similarity);
                        }
                    }
                }
            }

            // Sort recommended items based on the scores
            Map<Product, Double> sortedRecommendationScoresMap = recommendationScoresMap.entrySet().stream()
                    .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            recommendationScores.put(customer, sortedRecommendationScoresMap);
        }

        return recommendationScores;
    }

    // Calculate similarity between a viewed item and a set of purchased items
    private double calculateItemSimilarity(Map<String, Map<String, Double>> similarityMatrix, String viewedItemName, Set<Product> purchasedItems) {
        double similarityScore = 0.0;
        for (Product purchasedItem : purchasedItems) {
            similarityScore += similarityMatrix.getOrDefault(purchasedItem.getName(), Collections.emptyMap()).getOrDefault(viewedItemName, 0.0);
        }
        return similarityScore;
    }
}
