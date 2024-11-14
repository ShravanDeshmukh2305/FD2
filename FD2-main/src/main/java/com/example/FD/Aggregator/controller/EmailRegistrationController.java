package com.example.FD.Aggregator.controller;

import com.example.FD.Aggregator.dto.email.RegisterEmailRequestDTO;
import com.example.FD.Aggregator.dto.email.RegisterEmailResponseDTO;
import com.example.FD.Aggregator.dto.email.VerifyEmailOTPRequestDTO;
import com.example.FD.Aggregator.dto.email.VerifyEmailOTPResponseDTO;
import com.example.FD.Aggregator.service.EmailRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailRegistrationController {

    @Autowired
    private EmailRegistrationService emailRegistrationService;

    @PostMapping("/generate")
    public RegisterEmailResponseDTO generateEmailOTP(@RequestBody RegisterEmailRequestDTO requestDTO) {
        try {
            String result = emailRegistrationService.generateEmailOTP(requestDTO.getEmail(), requestDTO.getRefNo());

            RegisterEmailResponseDTO.DataDTO dataDTO = new RegisterEmailResponseDTO.DataDTO(result);
            RegisterEmailResponseDTO.SuccessData successData = new RegisterEmailResponseDTO.SuccessData("200", "Email OTP generated successfully", dataDTO);

            return new RegisterEmailResponseDTO(successData, null);
        } catch (Exception e) {
            return new RegisterEmailResponseDTO(null, "Failed to generate Email OTP.");
        }
    }


    @PostMapping("/verify")
    public VerifyEmailOTPResponseDTO verifyEmailOTP(@RequestBody VerifyEmailOTPRequestDTO requestDTO) {
        VerifyEmailOTPResponseDTO response = emailRegistrationService.verifyEmailOTP(
                requestDTO.getEmailOTP(),
                requestDTO.getRefNo(),
                requestDTO.getPreferredLang()
        );

        if (response == null) {
            response = new VerifyEmailOTPResponseDTO();
            response.setSuccess(null);
            response.setError("Email verification failed");
        }
        return response;
    }
}

