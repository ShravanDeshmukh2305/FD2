package com.example.FD.Aggregator.dto;


import lombok.Data;

@Data
public class GetUserResponseDTO {
    private String message;
    private boolean success;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;

    // Constructor to initialize all fields
    public GetUserResponseDTO(String message, boolean success, String email, String firstName, String lastName, String mobile) {
        this.message = message;
        this.success = success;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
    }

    // Constructor for only message and success
    public GetUserResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Default constructor (optional, for deserialization)
    public GetUserResponseDTO() {
    }
}