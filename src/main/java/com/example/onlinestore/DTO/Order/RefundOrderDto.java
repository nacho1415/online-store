package com.example.onlinestore.DTO.Order;

import lombok.Data;

@Data
public class RefundOrderDto {
    Long orderId;
    String impUID;
    Number amount;
}
