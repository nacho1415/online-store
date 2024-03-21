package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.Order.CreateOrderDto;
import com.example.onlinestore.DTO.Order.RefundOrderDto;
import com.example.onlinestore.Domain.Order;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String token, @RequestBody CreateOrderDto createOrderDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Order order = orderService.createOrder(user.getId(), createOrderDto);
            return ResponseEntity.ok(order);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createOrder failed: " + ex.getMessage());
        }
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundOrder(@RequestHeader("Authorization") String token, @RequestBody RefundOrderDto refundOrderDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Order order = orderService.refundOrder(user.getId(), refundOrderDto);
            return ResponseEntity.ok(order);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createOrder failed: " + ex.getMessage());
        }
    }
}
