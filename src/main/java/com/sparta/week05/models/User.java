package com.sparta.week05.models;


import com.sparta.week05.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity                 // DB 테이블 역할을 합니다.
@Setter
@Getter                 // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor      // 기본 생성자를 만들어 줍니다.
public class User extends Timestamped {

    // Id가 자동으로 생성 및 증가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 반드시 값을 가지도록합니다.
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private Long kakaoId;

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = null;
    }

    public User(String username, String password, String email, UserRole role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }
}
