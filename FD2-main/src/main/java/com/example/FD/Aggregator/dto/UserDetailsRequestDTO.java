package com.example.FD.Aggregator.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailsRequestDTO {
    private String refId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
