package com.example.onlinestore.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class TokenProvider {

    // Secret Key
    private final String secretKey = "YourSecretKey";

    // 토큰 유효 시간 (30분)
    private final long tokenValidTime = 30 * 60 * 1000L;

    // 토큰 생성
    public void createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidTime);

        Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 유저 정보 추출
    public UserDetails getUserDetails(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return new User(claims.getSubject(), "", List.of()); // User 클래스는 UserDetails 인터페이스를 구현한 것으로 가정
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}