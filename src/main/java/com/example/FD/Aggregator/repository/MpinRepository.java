package com.example.FD.Aggregator.repository;

import com.example.FD.Aggregator.entity.Mpin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MpinRepository extends JpaRepository<Mpin, Long> {
    Optional<Mpin> findByUser_RefNo(String refNo);

    Optional<Mpin> findByMpin(String mpin);
}
