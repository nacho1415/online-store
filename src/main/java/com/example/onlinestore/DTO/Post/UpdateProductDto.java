package com.example.onlinestore.DTO.Post;

import lombok.Data;

@Data
public class UpdateProductDto {
    Long id;
    String name;
    Integer price ;
    String description;
    Integer quantity;
}
