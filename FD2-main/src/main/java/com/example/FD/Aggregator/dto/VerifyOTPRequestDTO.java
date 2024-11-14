package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class VerifyOTPRequestDTO {
    private String otp;    // OTP code
    private String refNo;  // Reference number
}
