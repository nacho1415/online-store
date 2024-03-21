package com.example.onlinestore.Service;

import com.example.onlinestore.DTO.Order.CreateOrderDto;
import com.example.onlinestore.DTO.Order.RefundOrderDto;
import com.example.onlinestore.Domain.Order;
import com.example.onlinestore.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order createOrder (Long userId, CreateOrderDto createOrderDto) throws Exception {
        try {
            Order order = new Order();
            order.setAmount(createOrderDto.getAmount());
            order.setImpUID(createOrderDto.getImpUID());
            orderRepository.save(order);
            return order;
        } catch (Exception ex){
            throw new Exception("createOrder 도중 오류 발생");
        }
    }

//    public Order refundOrder (long userId, RefundOrderDto refundOrderDto) throws Exception {
//        try {
//            isOrderOwner(userId, refundOrderDto.getOrderId());
//        } catch (Exception ex){
//            throw new Exception("createOrder 도중 오류 발생");
//        }
//    }
//
//    public Boolean isOrderOwner (Long orderUserId, Long inputUserId) throws Exception {
//        try {
//            if (orderRepository.findOrderById(orderUserId) == null) {
//                throw new Exception("해당 Id를 가진 주문을 ")
//            }
//        } catch (Exception ex) {
//            throw new Exception("isOrderOwner 작업 중 오류 발생");
//        }
//    }
}
