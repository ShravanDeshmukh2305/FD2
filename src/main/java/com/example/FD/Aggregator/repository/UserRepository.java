package com.example.FD.Aggregator.repository;

import com.example.FD.Aggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
}


