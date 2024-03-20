package com.example.onlinestore.Controller;

import com.example.onlinestore.DTO.User.LoginDto;
import com.example.onlinestore.DTO.User.SignUpDto;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody SignUpDto signUpDto) {
        try {
            System.out.println(signUpDto + "확인용2222222222");
            userService.signUp(signUpDto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            // 예외 처리 로직
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDto loginDto) throws Exception {
        System.out.println("login Controller 확인" + loginDto);
        Map<String, Object> data = userService.login(loginDto);
        return ResponseEntity.ok(data);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // 예외 처리 후 클라이언트에게 오류 응답 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
