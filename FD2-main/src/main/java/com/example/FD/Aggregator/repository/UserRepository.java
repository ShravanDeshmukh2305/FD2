package com.example.FD.Aggregator.repository;

import com.example.FD.Aggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRefNo(String refNo); // Find user by reference number
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}


