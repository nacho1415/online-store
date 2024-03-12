package com.example.onlinestore.DTO.User;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
public class SignUpDto {
    String email;
    String password;
    String nickname;
}
