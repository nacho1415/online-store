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
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login () {
//
//    }

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
    public ResponseEntity<?> login (@RequestBody LoginDto loginDto) {
        try {
            System.out.println("login 메서드 시작");
//            System.out.println(userService.login(loginDto));
            Map<String, Object> reponseData = userService.login(loginDto);
//            System.out.println(user +"??");
            return ResponseEntity.ok(reponseData);
        } catch (Exception e) {
            System.out.println("컨트롤러실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed: " + e.getMessage());
        }
    }
}
