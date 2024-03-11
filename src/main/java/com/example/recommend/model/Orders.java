package com.example.recommend.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "productIdx")
    private Product product;
    @OneToOne
    @JoinColumn(name = "customerIdx")
    private Customer customer;
}
