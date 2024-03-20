package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.Like.AddLikeProductDto;
import com.example.onlinestore.DTO.Like.CancelLikeProductDto;
import com.example.onlinestore.DTO.Like.LikeCheckDto;
import com.example.onlinestore.Domain.Like;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    LikeService likeService;

    @PostMapping("/addLike")
    public ResponseEntity<?> addLike (@RequestHeader("Authorization") String token, @RequestBody AddLikeProductDto addLikeProductDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Like like = likeService.addLikeProduct(user, addLikeProductDto);
            return ResponseEntity.ok(like);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("addLike failed: " + ex.getMessage());
        }
    }

    @PostMapping("/cancelLike")
    public ResponseEntity<?> cancelLike (@RequestHeader("Authorization") String token, @RequestBody CancelLikeProductDto cancelLikeProductDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            likeService.cancelLikeProduct(user.getId(), cancelLikeProductDto.getProductId());
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cancelLike failed: " + ex.getMessage());
        }
    }

    @PostMapping("/likeCheck")
    public ResponseEntity<?> likeCheck (@RequestHeader("Authorization") String token, @RequestBody LikeCheckDto likeCheckDto) {
        try {
            User user = jwtTokenProvider.validateToken(token);
            likeService.LikeCheck(user.getId(), likeCheckDto.getProductId());
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("likeCheck failed: " + ex.getMessage());
        }
    }
}
