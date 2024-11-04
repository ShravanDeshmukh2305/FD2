package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.*;
import com.example.FD.Aggregator.service.MobileRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile")
public class MobileRegistrationController {

    @Autowired
    private MobileRegistrationService mobileRegistrationService;

    @PostMapping("/register")
    public RegisterMobileResponseDTO registerMobile(@RequestBody RegisterMobileRequestDTO requestDTO) {
        String uuid = mobileRegistrationService.generateOTP(requestDTO.getMobile());
        RegisterMobileResponseDTO response = new RegisterMobileResponseDTO();
        response.setMessage("Registration successful");
        response.setUuid(uuid);
        return response;
    }

    @PostMapping("/verify")
    public VerifyOTPResponseDTO verifyOTP(@RequestBody VerifyOTPRequestDTO requestDTO) {
        VerifyOTPResponseDTO response = mobileRegistrationService.verifyOTP(requestDTO.getOtp()); // Only pass the OTP
        if (response == null) {
            // Handle the case where verification failed
            response = new VerifyOTPResponseDTO();
            response.setSuccess(null); // No success response
            response.setError("Verification failed"); // Set an appropriate error message
        }
        return response;
    }

    // Controller
    @PostMapping("/user-details")
    public UserDetailsResponseDTO saveUserDetails(@RequestBody UserDetailsRequestDTO requestDTO) {
        return mobileRegistrationService.saveUserDetails(requestDTO);
    }

    @PutMapping("/user-details-update")
    public UserDetailsResponseDTO updateUserProfile(@RequestBody UpdateUserProfileRequestDTO requestDTO) {
        return mobileRegistrationService.updateUserProfile(requestDTO);
    }

    @GetMapping("/user-by-email")
    public GetUserResponseDTO getUserByEmail(@RequestParam String email) {
        return mobileRegistrationService.getUserByEmail(email);
    }

    @GetMapping("/user-by-mobile")
    public GetUserResponseDTO getUserByMobile(@RequestParam String mobile) {
        return mobileRegistrationService.getUserByMobile(mobile);
    }

}
