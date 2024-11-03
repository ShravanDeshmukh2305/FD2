package com.example.FD.Aggregator.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailsRequestDTO {
    private String refId; // Received reference ID
    private String mobile; // Mobile number (optional if you want to pass)
    private String email; // User's email
    private String firstName; // User's first name
    private String lastName; // User's last name
    private LocalDate dob; // User's date of birth
}
