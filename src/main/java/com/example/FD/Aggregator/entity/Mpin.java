package com.example.FD.Aggregator.entity;

import lombok.Data;


import java.time.LocalDateTime;

import jakarta.persistence.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Table(name = "mpin")
@Data // Lombok will generate getters, setters, toString, equals, and hashCode methods
public class Mpin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID as primary key

    @Column(name = "ref_no", nullable = false)
    private Long refNo; // Ref no from the user table

    @Column(name = "user_ref_id", nullable = false)
    private Long userRefId; // Additional reference for user

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid; // Unique identifier

    @Column(name = "mpin", nullable = false)
    private String mpin; // The MPIN value

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked; // Indicates if the MPIN is locked

    @Column(name = "retry", nullable = false)
    private Integer retry; // Number of retry attempts

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Creation timestamp

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Update timestamp

}

