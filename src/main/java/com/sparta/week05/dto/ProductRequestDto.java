package com.sparta.week05.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequestDto {

    private final String title;
    private final String image;
    private final String link;
    private final int lprice;
}
