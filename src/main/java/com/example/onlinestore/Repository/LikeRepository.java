package com.example.onlinestore.Repository;

import com.example.onlinestore.Domain.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Like like) {
        em.persist(like);
    }
}
