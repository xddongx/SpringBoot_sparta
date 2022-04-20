package com.sparta.week05.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter                 // get 함수를 일괄적으로 만들어 줌
@Entity                 // DB 테이블 역할을 함
@NoArgsConstructor      // 기본 생성자를 만들어 줌
public class UserTime {
    // ID가 자동으로 생성 및 증가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private long totalTime;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private long totalCount;

    public UserTime(User user, long totalTime) {
        this.user = user;
        this.totalTime = totalTime;
        this.totalCount = 1;
    }

    public void updateTotalTime(long totalTime, long totalCount) {
        this.totalTime = totalTime;
        this.totalCount = totalCount;
    }
}
