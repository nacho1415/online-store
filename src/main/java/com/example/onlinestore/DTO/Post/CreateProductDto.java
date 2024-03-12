package com.example.onlinestore.DTO.Post;

import lombok.Data;

@Data
public class CreateProductDto {

    String name;
    Integer price;
    String description;
    Integer quantity;
}
