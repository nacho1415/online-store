package com.example.onlinestore.Repository;

import com.example.onlinestore.Domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }
}
