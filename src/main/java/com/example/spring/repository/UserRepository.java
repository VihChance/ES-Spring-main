package com.example.spring.repository;

import com.example.spring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    User findByEmail(String email);
      Optional<User> findByEmail(String email);

}
