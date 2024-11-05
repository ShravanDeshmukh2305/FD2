package com.example.FD.Aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPDetailsDTO {
    private String mobile;
    private String otp;
    private boolean isVerified;
}

