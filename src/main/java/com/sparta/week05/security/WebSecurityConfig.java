package com.sparta.week05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity                      // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/imgaes/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // user로 시작되는 페이지는 인증없이 시작 가능하게
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()                   // 어떤 요청이 오던지 로그인 과정이 없으면 로그인을 하겠다.
                .and()
                .formLogin()                                    // 로그인 페이지에 대해서는
                .loginPage("/user/login")                       // 로그인이 필요할 때 피요한 위치를 지정
                .failureUrl("/user/login/error")                // 로그인에 실패 했을 때
                .defaultSuccessUrl("/")                         // 로그인 완료 후 home
                .permitAll()                                    // 허용
                .and()
                .logout()                                       // 로그아웃
                .logoutUrl("/user/logout")                      // 로그아웃 url
                .permitAll()                                    // 허용
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden");           // 인기가 되지 않았을 때 ㅕ기
    }
}
