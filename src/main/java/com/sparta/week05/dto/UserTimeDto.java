package com.sparta.week05.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTimeDto {
    String username;
    long totalTime;
}
