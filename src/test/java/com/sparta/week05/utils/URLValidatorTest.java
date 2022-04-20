package com.sparta.week05.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class URLValidatorTest {

    @Test
    @DisplayName("URL 형태: 정상")
    void urlValidator1() {
        // given
        String url = "https://shopping-phinf.pstatic.net/main_8232398/82323985017.4.jpg";
        // when
        boolean isValid = URLValidator.urlValidator(url);
        // then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("URL 형태: 비정상 (null인 경우)")
    void urlValidator2() {
        // give
        String url = null;
        // when
        boolean isValid = URLValidator.urlValidator(url);
        // then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("URL 형태: 비정상(빈 문자열)")
    void urlValidator3() {
        // give
        String url = "";
        // when
        boolean isValid = URLValidator.urlValidator(url);
        // then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("URL 형태: 비정상(일반 문자열)")
    void urlValidator4() {
        // give
        String url = "단위 테스트";
        // when
        boolean isValid = URLValidator.urlValidator(url);
        // then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("URL 형태: 비정상('://' 빠짐)")
    void urlValidator5() {
        // give
        String url = "httpfacebook.com";
        // when
        boolean isValid = URLValidator.urlValidator(url);
        // then
        assertFalse(isValid);
    }
}