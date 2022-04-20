package com.sparta.week05.models;

import com.sparta.week05.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter                 // get 함수를 일괄적으로 만들어 줌
@Entity                 // DB 테이블 역할
@NoArgsConstructor      // 기본 생성자를 만들어 줌
public class Folder extends Timestamped {

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }

    // ID가 자동으로 생성 및 증가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 반드시 값을 가지도록 함함
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
