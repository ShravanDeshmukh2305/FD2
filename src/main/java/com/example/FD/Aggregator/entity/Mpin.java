package com.example.FD.Aggregator.entity;

import lombok.Data;


import java.time.LocalDateTime;

import jakarta.persistence.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "mpin")
@Data
public class Mpin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented primary key
    @Column(name = "ref_id", nullable = false, updatable = false)
    private Long refId; // Primary key in the mpin table

    @ManyToOne
    @JoinColumn(name = "user_ref_id", referencedColumnName = "ref_id", nullable = false)
    private User user; // Foreign key from the User table

    @Column(name = "mpin", nullable = false)
    private String mpin; // The MPIN value

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked; // Indicates if the MPIN is locked

    @Column(name = "retry", nullable = false)
    private Integer retry; // Number of retry attempts

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Creation timestamp with default current date

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Update timestamp
}