package com.sparta.week05.controller;

import com.sparta.week05.dto.UserTimeDto;
import com.sparta.week05.models.User;
import com.sparta.week05.models.UserTime;
import com.sparta.week05.repository.UserTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserTimeController {
    private final UserTimeRepository userTimeRepository;

    @Autowired
    public UserTimeController(UserTimeRepository userTimeRepository) {
        this.userTimeRepository = userTimeRepository;
    }

    // (관리자용) 회원별 API 수행 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/user/time")
    public List<UserTimeDto> getUserTime() {
        List<UserTime> allUserTime = userTimeRepository.findAll();

        // UserTime -> UserTimeDto로 반환
        List<UserTimeDto> allUserTimeDto = new ArrayList<>();
        for (UserTime userTime : allUserTime) {
            String username = userTime.getUser().getUsername();
            long totalTime = userTime.getTotalTime();
            UserTimeDto dto = new UserTimeDto(username, totalTime);
            allUserTimeDto.add(dto);
        }

        return allUserTimeDto;
    }

}
