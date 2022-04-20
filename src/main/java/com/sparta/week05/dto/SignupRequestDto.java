package com.sparta.week05.dto;

import lombok.*;

@Getter
@Setter
//@RequiredArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
