package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class UpdateUserProfileRequestDTO {
    private String refId; // Reference ID to identify the user
    private String email; // New email
    private String firstName; // New first name
    private String lastName; // New last name
}
