package com.example.FD.Aggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "usersotp") // Specify table name
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobile;
    private String refNo;
    private String email;
    private String firstName;
    private String lastName;

    @Column(name = "date_of_birth") // For better column name mapping
    private LocalDate dob;

    // Getters and setters (optional if using Lombok)
}