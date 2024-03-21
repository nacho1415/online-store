package com.example.onlinestore.Repository;

import com.example.onlinestore.DTO.Order.CreateOrderDto;
import com.example.onlinestore.Domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Order order) {
        em.persist(order);
    }

    public Order findOrderById (Long id) throws Exception {
            Order order;
        try {
            order = em.createQuery("SELECT o FROM Order WHERE u.id = :id", Order.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return order;
        } catch (NoResultException ex) {
            return order = null;
        } catch (Exception ex) {
            throw new Exception("findOrderById 작업 중 에러 발생");
        }
    }
}
