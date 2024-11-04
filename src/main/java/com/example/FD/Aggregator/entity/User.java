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
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ref_no", unique = true, nullable = false)
    private String refNo; // refNo as primary key

    private String mobile;
    private String email;
    private String firstName;
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dob; // User's date of birth
}