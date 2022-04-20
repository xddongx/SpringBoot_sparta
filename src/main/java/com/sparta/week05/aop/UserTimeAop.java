package com.sparta.week05.aop;

import com.sparta.week05.models.User;
import com.sparta.week05.models.UserTime;
import com.sparta.week05.repository.UserTimeRepository;
import com.sparta.week05.security.UserDetailsImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component                  // 스프링 IoC에 빈으로 등록
public class UserTimeAop {

    private final UserTimeRepository userTimeRepository;

    public UserTimeAop(UserTimeRepository userTimeRepository) {
        this.userTimeRepository = userTimeRepository;
    }

    /*
    * 패키지명의 public 함수에만 적용
    */
    @Around("execution(public * com.sparta.week05.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();
            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;
            // 로긍인 회원이 없는 경우, 수행시간 기록하지 않음
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                // 로그인 회원 -> loginUser 변수
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();

                // 수행시간 및 DB에 기록
                UserTime userTime = userTimeRepository.findByUser(loginUser);
                if (userTime != null) {
                    // 로그인 회원의 기록이 있으면...

                    // API 전체 수행 시간
                    long totalTime = userTime.getTotalTime();
//                    totalTime = totalTime + runTime;
                    totalTime += runTime;

                    // API 전체 수행 횟수
                    long totalCount = userTime.getTotalCount();
                    totalCount++;

                    userTime.updateTotalTime(totalTime, totalCount);
                } else {
                    // 로그인 회원의 기록이 없으면
                    userTime = new UserTime(loginUser, runTime);
                }

                System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + "ms");
                userTimeRepository.save(userTime);
            }
        }
    }
}
