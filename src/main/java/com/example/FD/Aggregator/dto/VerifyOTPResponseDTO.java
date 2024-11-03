package com.example.FD.Aggregator.dto;

import lombok.Data;


@Data
public class VerifyOTPResponseDTO {
    private SuccessResponseDTO success;
    private Object error; // This can be a specific error DTO if needed
}