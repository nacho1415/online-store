package com.example.onlinestore.Repository;

import com.example.onlinestore.DTO.User.SignUpDto;
import com.example.onlinestore.Domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern passworPattern = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidPassword(String password) {
        Matcher matcher = passworPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public void save (User user) {
        em.persist(user);
    }

   //닉네임, 이메일 중복체크
    public void duplicateCheck (SignUpDto signUpDto) {
        System.out.println(signUpDto);
        System.out.println(signUpDto.getEmail());
        Long EmailDuplicateCount = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = : email", Long.class)
                .setParameter("email", signUpDto.getEmail())
                .getSingleResult();
        if (EmailDuplicateCount > 0) {
            throw new IllegalArgumentException("email duplicate");
        }
        Long nicknameDuplicateCount = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.nickname = : nickname", Long.class)
                .setParameter("nickname", signUpDto.getNickname())
                .getSingleResult();
        if (nicknameDuplicateCount > 0) {
            throw new IllegalArgumentException("nickname is duplicate");
        }

    }

    public User findByEmail (String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            // 해당 이메일을 가진 사용자를 찾지 못한 경우
            return null;
        }
    }

    public Boolean isSamePassword (String inputPassword, String databasePassword) {
        String encryptionPassword = passwordEncoder.encode(inputPassword);
        if (encryptionPassword.equals(databasePassword)) {
            return true;
        } else {
            return false;
        }
    }

    public void validateCheck (SignUpDto signUpDto) {
        if (signUpDto.getEmail().length() <= 0 && !isValidEmail(signUpDto.getEmail())) {
            throw new IllegalArgumentException("email is valid");
        }

        if (signUpDto.getPassword().length() <= 0 || !isValidPassword(signUpDto.getPassword())) {
            throw new IllegalArgumentException("password is valid");
        }

        if (signUpDto.getNickname().length() <= 0) {
            throw new IllegalArgumentException("nickname is valid");
        }
    }

//    public boolean deleteUser () {
//
//    }

}
