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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ProductService productService;

    @GetMapping("/createProduct")
    public String createProduct () {
        return "product/createProduct";
    }

    @GetMapping("/{productId}")
    public String ProductDetail (@PathVariable("productId") Long productId, Model model) {
        Product product = productService.findProduct(productId);
        System.out.println(product);
        model.addAttribute("product", product);
        return "product/productDetail"; // 실제 뷰 이름을 반환
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct (@RequestHeader("Authorization") String token, @RequestBody CreateProductDto createProductDto) throws Exception {
        try {
            System.out.println(token + "확인" + createProductDto);
            User user = jwtTokenProvider.validateToken(token);
            Product product = productService.createProduct(user, createProductDto);
            return ResponseEntity.ok(product);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createProduct failed: " + ex.getMessage());
        }
    }

//    @PostMapping("/findAllProduct")
//    public ResponseEntity<?> findAllProduct (@Valid @RequestBody CreateProductDto createProductDto) throws Exception {
//        try {
//            List<Product> products = productService.findAllProduct();
//            return ResponseEntity.ok(products);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("createProduct failed: " + ex.getMessage());
//        }
//    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // 예외 처리 후 클라이언트에게 오류 응답 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
