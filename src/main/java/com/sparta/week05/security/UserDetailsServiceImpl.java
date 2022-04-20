package com.sparta.week05.security;

import com.sparta.week05.models.User;
import com.sparta.week05.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    /*
    * spring security가 보내준 username(Id)을 가지고 DB에서 찾아
    * 회원이 있는지 조회
    * username(Id)을 찾을 수 없으면 error
    * 있다면 UserDetailsImpl에 반환
    */

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Can't find" + username)
        );

        return new UserDetailsImpl(user);
    }
}
