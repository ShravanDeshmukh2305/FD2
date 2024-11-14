package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class UpdateUserProfileRequestDTO {
    private String refId;
    private String email;
    private String firstName;
    private String lastName;
}
