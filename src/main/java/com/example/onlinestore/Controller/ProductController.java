package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.Post.CreateProductDto;
import com.example.onlinestore.DTO.Post.UpdateProductDto;
import com.example.onlinestore.Domain.Product;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct (@RequestHeader("Authorization") String token, @Valid @RequestBody CreateProductDto createProductDto) throws Exception {
        try {
            User user = jwtTokenProvider.validateToken(token);
            Product product = productService.createProduct(user, createProductDto);
            return ResponseEntity.ok(product);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createProduct failed: " + ex.getMessage());
        }
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<?> updateProduct (@RequestHeader("Authorization") String token, @RequestBody UpdateProductDto updateProductDto) {
        try {
            jwtTokenProvider.validateToken(token);
            productService.updateProduct(updateProductDto);
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("updateProduct failed: " + ex.getMessage());
        }
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct (@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> requestBody) {
        try {
            System.out.println("상품 삭제 시도는 됨");
            Long productId = Long.parseLong(requestBody.get("id").toString());
            User user = jwtTokenProvider.validateToken(token);
            productService.deleteProduct(user.getId(), productId);
            return ResponseEntity.ok("success");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("deleteProduct failed: " + ex.getMessage());
        }
    }
}
