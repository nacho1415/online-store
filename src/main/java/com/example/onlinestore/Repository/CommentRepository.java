package com.example.onlinestore.Repository;

import com.example.onlinestore.Domain.Comment;
import com.example.onlinestore.Domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public boolean isCommentOwner (Long userId, Long commentId) {
        try {
            Comment comment = findCommentById(commentId);
            if (comment.getUser().getId() == userId) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("댓글의 주인인지 판별하는 도중 오류가 발생하였습니다");
        }
    }

    @Transactional
    public void deleteComment (Long id) {
        try {
            System.out.println(id + "확인용");
            em.createQuery("DELETE FROM Comment c WHERE c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("댓글을 삭제하는도중 오류가 발생했습니다");
        }
    }

    public Comment findCommentById (Long id) throws Exception {
        try {
            Comment comment = em.createQuery("SELECT c FROM Comment c WHERE c.id = :id", Comment.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return comment;
        } catch (Exception ex) {
            throw new Exception("댓글을 찾는도중 오류가 발생했습니다" + ex.getMessage());
        }
    }

}
