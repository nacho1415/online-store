package com.example.onlinestore.Domain;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;
}
