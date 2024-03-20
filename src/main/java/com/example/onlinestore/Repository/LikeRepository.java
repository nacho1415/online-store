package com.example.onlinestore.Repository;

import com.example.onlinestore.Domain.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LikeRepository {

    @PersistenceContext
    EntityManager em;

    public Like findLikeById (Long productId, Long userId) throws Exception {
        Like like;
        try {
            like = em.createQuery("SELECT l FROM Like l WHERE l.user.id = :userId AND l.product.id = :productId", Like.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
            return like;
        } catch (NoResultException ex) {
            return like = null ;
        } catch (Exception ex) {
            throw new Exception("findLikeById를 하는 도중 오류 발생");
        }
    }

    public Like findLikeByLikeId (Long likeId) throws Exception {
        List<Like> results = em.createQuery("SELECT l FROM Like l WHERE l.id = :likeId", Like.class)
                .setParameter("likeId", likeId)
                .getResultList();
        System.out.println(results);
        if (results.isEmpty()) {
            throw new Exception("해당 상품에는 좋아요를 표시하지 않았습니다");
        } else {
            return results.get(0);
        }
    }


    @Transactional
    public void deleteLikeById (Long id) {
        try {
            System.out.println(id + "확인용");
            em.createQuery("DELETE FROM Like l WHERE l.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

        } catch (Exception ex) {
            throw new IllegalArgumentException("like를 삭제하는도중 오류가 발생했습니다");
        }
    }

    public void save (Like like) {
        em.persist(like);
    }
}
