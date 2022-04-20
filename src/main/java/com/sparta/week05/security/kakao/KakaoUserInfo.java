package com.sparta.week05.security.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {

    Long id;
    String email;
    String nickname;
}
