package com.example.FD.Aggregator.repository;

import com.example.FD.Aggregator.entity.Mpin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MpinRepository extends JpaRepository<Mpin, Long> {
    // You can define custom query methods here if needed
}
