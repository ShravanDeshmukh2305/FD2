package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class UserDetailsResponseDTO {
    private String message;
    private boolean success;

    // Constructor to initialize the fields
    public UserDetailsResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
