package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.*;
import com.example.FD.Aggregator.service.MobileRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mobile")
public class MobileRegistrationController {

    @Autowired
    private MobileRegistrationService mobileRegistrationService;

    @PostMapping("/register")
    public RegisterMobileResponseDTO registerMobile(@RequestBody RegisterMobileRequestDTO requestDTO) {
        try {
            String uuid = mobileRegistrationService.generateOTP(requestDTO.getMobile());


            RegisterMobileResponseDTO.DataDTO dataDTO = new RegisterMobileResponseDTO.DataDTO(uuid);
            RegisterMobileResponseDTO.SuccessData successData = new RegisterMobileResponseDTO.SuccessData("200", "Registration successful", dataDTO);

            return new RegisterMobileResponseDTO(successData, null);
        } catch (Exception e) {

            return new RegisterMobileResponseDTO(null, "Failed to generate OTP.");
        }
    }


    @PostMapping("/verify")
    public VerifyOTPResponseDTO verifyOTP(@RequestBody VerifyOTPRequestDTO requestDTO) {
        VerifyOTPResponseDTO response = mobileRegistrationService.verifyOTP(requestDTO.getOtp(), requestDTO.getRefNo());
        if (response == null) {
            response = new VerifyOTPResponseDTO();
            response.setSuccess(null);
            response.setError("Verification failed");
        }
        return response;
    }


    @PostMapping("/user-details")
    public UserDetailsResponseDTO saveUserDetails(@RequestBody UserDetailsRequestDTO requestDTO) {
        return mobileRegistrationService.saveUserDetails(requestDTO);
    }


    @PutMapping("/user-details-update")
    public UpdateUserProfileResponseDTO updateUserProfile(@RequestBody UpdateUserProfileRequestDTO requestDTO) {
        return mobileRegistrationService.updateUserProfile(requestDTO);
    }


    @PostMapping("/user-by-email")
    public GetUserResponseDTO getUserByEmail(@RequestBody GetUserByEmailRequestDTO requestDTO) {
        return mobileRegistrationService.getUserByEmail(requestDTO.getEmail());
    }

    @PostMapping("/user-by-mobile")
    public GetUserResponseDTO getUserByMobile(@RequestBody GetUserByMobileRequestDTO requestDTO) {
        return mobileRegistrationService.getUserByMobile(requestDTO.getMobile());
    }

}
