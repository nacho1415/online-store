package com.example.onlinestore.User;

import com.example.onlinestore.DTO.User.LoginDto;
import com.example.onlinestore.DTO.User.SignUpDto;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.Repository.UserRepository;
import com.example.onlinestore.Service.TokenProvider;
import com.example.onlinestore.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;

    @Test
    public void 토큰만들기 () {
        tokenProvider.createToken("Token");
    }

    @Test

    public void 회원가입테스트 () {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fdsafds@naver.com");
        signUpDto.setPassword("Wqejq1231@4");
        signUpDto.setNickname("adsd");
        userService.signUp(signUpDto);

    }

    @Test
    public void 로그인테스트 () throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("fdsafds@naver.com");
        loginDto.setPassword("Wqejq1231@4");
        Map<String, Object> user = userService.login(loginDto);
        System.out.println(user);
    }
}
