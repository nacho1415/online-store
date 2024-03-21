package com.example.onlinestore.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUID;

    private Number amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;
}
