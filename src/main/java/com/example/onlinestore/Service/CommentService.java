package com.example.onlinestore.Service;

import com.example.onlinestore.DTO.Comment.CreateCommentDto;
import com.example.onlinestore.DTO.Comment.UpdateCommentDto;
import com.example.onlinestore.Domain.Comment;
import com.example.onlinestore.Repository.CommentRepository;
import com.example.onlinestore.Repository.ProductRepository;
import com.example.onlinestore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Comment createComment (CreateCommentDto createCommentDto, Long userId) {
        Comment comment = new Comment();
        comment.setContext(createCommentDto.getContext());
        comment.setUser(userRepository.findById(userId));
        comment.setProduct(productRepository.findProductById(createCommentDto.getProduct()));
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long userId, Long commentId) throws Exception {
        try {
            if (commentRepository.isCommentOwner(userId, commentId) == false) {
                throw new IllegalArgumentException("상품의 주인이 아닙니다");
            }
            commentRepository.deleteComment(commentId);
        } catch (Exception ex) {
            throw new Exception("fail reason = " + ex.getMessage());
        }
    }

    public Boolean isCommentOwner(Long userId, Long commentId) throws Exception {
        try {
            Comment findComment = commentRepository.findCommentById(commentId);
            if (findComment.getId() == userId) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception("댓글의 주인을 판별하는도중 오류 발생");
        }
    }


    @Transactional
    public Comment updateComment (UpdateCommentDto updateCommentDto, Long userId) throws Exception {
        Comment comment = commentRepository.findCommentById(updateCommentDto.getCommentId());
        if (!isCommentOwner(userId, comment.getId())) {
            throw new Exception("댓글의 주인이 수정 요청을 한게 아닙니다");
        }
        comment.setContext(updateCommentDto.getContext());
        return comment;
    }
}
