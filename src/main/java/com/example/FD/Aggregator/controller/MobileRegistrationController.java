//package com.example.FD.Aggregator.controller;
//
//import com.example.FD.Aggregator.dto.RegisterMobileRequestDTO;
//import com.example.FD.Aggregator.dto.RegisterMobileResponseDTO;
//import com.example.FD.Aggregator.dto.VerifyOTPRequestDTO;
//import com.example.FD.Aggregator.dto.VerifyOTPResponseDTO;
//import com.example.FD.Aggregator.service.MobileRegistrationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/mobile")
//public class MobileRegistrationController {
//
//    @Autowired
//    private MobileRegistrationService mobileRegistrationService;
//
//    @PostMapping("/register")
//    public RegisterMobileResponseDTO registerMobile(@RequestBody RegisterMobileRequestDTO requestDTO) {
//        String uuid = mobileRegistrationService.generateOTP(requestDTO.getMobile(), requestDTO.isFakeOTP());
//        RegisterMobileResponseDTO response = new RegisterMobileResponseDTO();
//        response.setMessage("Registration successful");
//        response.setUuid(uuid);
//        return response;
//    }
//
//    @PostMapping("/verify")
//    public VerifyOTPResponseDTO verifyOTP(@RequestBody VerifyOTPRequestDTO requestDTO) {
//        boolean isVerified = mobileRegistrationService.verifyOTPAndSave(requestDTO.getUuid(), requestDTO.getOtp());
//        VerifyOTPResponseDTO response = new VerifyOTPResponseDTO();
//        response.setMessage(isVerified ? "Verification successful and user saved to DB" : "Verification failed");
//        response.setSuccess(isVerified);
//        return response;
//    }
//}


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

    @PostMapping("/user-details")
    public UserDetailsResponseDTO saveUserDetails(@RequestBody UserDetailsRequestDTO requestDTO) {
        return mobileRegistrationService.saveUserDetails(requestDTO);
    }
}
