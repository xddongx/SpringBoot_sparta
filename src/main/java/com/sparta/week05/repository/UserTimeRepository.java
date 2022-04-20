package com.sparta.week05.repository;

import com.sparta.week05.models.User;
import com.sparta.week05.models.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
    UserTime findByUser(User user);
}
