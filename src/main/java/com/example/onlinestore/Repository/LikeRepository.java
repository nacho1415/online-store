package com.example.onlinestore.Repository;

import com.example.onlinestore.Domain.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LikeRepository {

    @PersistenceContext
    EntityManager em;

    public void findLikeById (Long productId, Long userId) throws Exception {
        List<Like> results = em.createQuery("SELECT l FROM Like l WHERE l.user.id = :userId AND l.product.id = :productId", Like.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getResultList();
        System.out.println("확인용33333333333" + results);
        if (!results.isEmpty()) {
            throw new Exception("이미 좋아요한 상품입니다");
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
