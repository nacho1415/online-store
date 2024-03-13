package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.Comment.CreateCommentDto;
import com.example.onlinestore.DTO.Comment.DeleteCommentDto;
import com.example.onlinestore.DTO.Comment.UpdateCommentDto;
import com.example.onlinestore.Domain.Comment;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Repository.CommentRepository;
import com.example.onlinestore.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/comment")
@Controller
public class CommentController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment (@RequestHeader("Authorization") String token, @RequestBody CreateCommentDto createCommentDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Comment comment = commentService.createComment(createCommentDto, user.getId());

            return ResponseEntity.ok(comment);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createComment failed: " + ex.getMessage());
        }
    }

    @PostMapping("/updateComment")
    public ResponseEntity<?> updateComment (@RequestHeader("Authorization") String token, @RequestBody UpdateCommentDto updateCommentDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Comment comment = commentService.updateComment(updateCommentDto, user.getId());
            return ResponseEntity.ok(comment);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("updateComment failed: " + ex.getMessage());
        }
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComment (@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> requestBody) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Long commentId = Long.parseLong(requestBody.get("commentId").toString());
            commentService.deleteComment(user.getId(), commentId);
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("deleteComment failed: " + ex.getMessage());
        }
    }

}
