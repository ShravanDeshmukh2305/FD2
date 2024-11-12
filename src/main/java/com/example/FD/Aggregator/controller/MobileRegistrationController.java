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
    public ResponseEntity<String> registerMobile(@RequestBody RegisterMobileRequestDTO requestDTO) {
        try {
             mobileRegistrationService.generateOTP(requestDTO.getMobile());

            return new ResponseEntity<>("Mobile Registration successful", HttpStatus.OK);
        } catch (Exception e) {

            log.error("Failed to generate OTP.", e);

            return new ResponseEntity<>("Failed to generate OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/verify")
    public ResponseEntity<SuccessResponseDTO> verifyOTP(@RequestBody VerifyOTPRequestDTO requestDTO) {
        SuccessResponseDTO response = mobileRegistrationService.verifyOTP(requestDTO.getOtp(), requestDTO.getRefNo());
        if (response == null) {
            return new ResponseEntity<>(null,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
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
