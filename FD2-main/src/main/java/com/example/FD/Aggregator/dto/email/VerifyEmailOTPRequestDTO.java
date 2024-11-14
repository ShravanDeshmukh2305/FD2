package com.example.FD.Aggregator.dto.email;

import lombok.Data;

@Data
public class VerifyEmailOTPRequestDTO {
    private String emailOTP;
    private String refNo;
    private String preferredLang;
}
