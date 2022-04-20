package com.sparta.week05.controller;

import com.sparta.week05.dto.ItemDto;
import com.sparta.week05.models.User;
import com.sparta.week05.models.UserTime;
import com.sparta.week05.repository.UserTimeRepository;
import com.sparta.week05.security.UserDetailsImpl;
import com.sparta.week05.security.UserDetailsServiceImpl;
import com.sparta.week05.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController                             // JSON으로 응답함을 선언합니다.
//@RequiredArgsConstructor                    // final로 선언된 클래스를 자동으로 생성합니다.
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;
//    private final UserTimeRepository userTimeRepository;

    @Autowired
    public SearchRequestController(NaverShopSearch naverShopSearch) {
        this.naverShopSearch = naverShopSearch;
    }

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query) {
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(resultString);
    }

//    @GetMapping("/api/search")
//    public List<ItemDto> getItems(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 측정 시작 시간
//        long startTime = System.currentTimeMillis();
//
//        try {
//            String resultString = naverShopSearch.search(query);
//            return naverShopSearch.fromJSONtoItems(resultString);
//        } finally {
//            // 측정 종료 시간
//            long endTime = System.currentTimeMillis();
//            // 수행시간 = 종료 시간 - 시작 시간
//            long runTime = endTime - startTime;
//            // 로그인 회원 정보
//            User loginUser = userDetails.getUser();
//
//            // 수행시간 및 DB에 기록
//            UserTime userTime = userTimeRepository.findByUser(loginUser);
//            if (userTime != null) {
//                // 로그인 회원의 기록이 있으면
//                long totaltime = userTime.getTotalTime();
//                totaltime = totaltime + runTime;
//                userTime.updateTotalTime(totaltime);
//            } else {
//                // 로그인 회원의 기록이 없으면
//                userTime = new UserTime(loginUser, runTime);
//            }
//
//            System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + "ms");
//            userTimeRepository.save(userTime);
//        }
//
//    }
}
