package com.example.FD.Aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing OTP details for mobile registration.
 */
@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a default constructor
@AllArgsConstructor // Generates a constructor with all fields as parameters
public class OTPDetailsDTO {
    private String mobile; // Mobile number of the user
    private String otp; // One-time password
    private boolean isVerified; // Verification status of the OTP
}
