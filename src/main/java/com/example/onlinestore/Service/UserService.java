package com.example.onlinestore.Service;

import com.example.onlinestore.DTO.User.LoginDto;
import com.example.onlinestore.DTO.User.SignUpDto;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp (SignUpDto signUpDto) {
        userRepository.duplicateCheck(signUpDto);
        userRepository.validateCheck(signUpDto);
        System.out.println("여기까지 오나?");
        User user = new User();
        user.setNickname(signUpDto.getNickname());
        user.setEmail(signUpDto.getEmail());
        System.out.println("여기까지 오나?2");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        System.out.println("여기까지 오나?3");
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void validateLoginDto (LoginDto loginDto) {
        if (loginDto.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (!Pattern.matches(EMAIL_PATTERN, loginDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (loginDto.getPassword() == null ||
                loginDto.getPassword().length() < MIN_PASSWORD_LENGTH ||
                loginDto.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    @Transactional
    public Map<String, Object> login (LoginDto loginDto) throws Exception {
        Map<String, Object> response = new HashMap<>();

        try {
            validateLoginDto(loginDto);
            System.out.println("여기까지도옴11111");
            User user = userRepository.findByEmail(loginDto.getEmail());
            if (user == null) {
                throw new IllegalArgumentException("해당 email을 가진 User는 존재하지 않습니다");
            }
            System.out.println("여기까지도옴222222");
            Long userId = user.getId();
            if (userRepository.isSamePassword(user.getPassword(), loginDto.getPassword())) {
                throw new IllegalArgumentException("password is wrong");
            }
            System.out.println("여기까지도옴33333");
            System.out.println("여기까지도옴44444");
            String token = jwtTokenProvider.generateToken(user.getEmail());
            System.out.println("여기까지도옴55555");
            response.put("token", token);
            response.put("user", user);
            response.put("userId", user.getId());
            System.out.println(response);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("fail reason = " + e.getMessage());
        }
    }
    public void deleteUser () {

    }


}
