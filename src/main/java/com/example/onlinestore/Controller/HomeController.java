package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.Post.CreateProductDto;
import com.example.onlinestore.Domain.Product;
import com.example.onlinestore.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping()
    public String findAllProduct (Model model) throws Exception {
        List<Product> products = productService.findAllProduct();
        System.out.println(products);
        model.addAttribute("products", products);
        return "product/main";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // 예외 처리 후 클라이언트에게 오류 응답 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
