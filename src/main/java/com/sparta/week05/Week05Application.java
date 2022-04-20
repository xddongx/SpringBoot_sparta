package com.sparta.week05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling                       // 스프링 분트에서 스케줄러가 작동하게 합니다.
@EnableJpaAuditing                      // 시간 자동 변경이 가능하도록 함
@SpringBootApplication                  // 스프링 부트임을 선언
public class Week05Application {

    public static void main(String[] args) {
        SpringApplication.run(Week05Application.class, args);
    }

}
