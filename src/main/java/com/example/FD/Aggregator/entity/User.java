package com.example.FD.Aggregator.entity;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ref_id", unique = true, nullable = false)
    private String refNo; // refNo as primary key

    private String mobile;
    private String email;
    private String firstName;
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dob; // User's date of birth
}