package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.config.AppConfig;
import com.example.FD.Aggregator.dto.*;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MobileRegistrationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppConfig appConfig;

    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON handling

    public String generateOTP(String mobile) {
        String uuid = UUID.randomUUID().toString();
        String otp = appConfig.isFakeotp() ? "123456" : generateActualOTP();

        // Create an instance of OTPDetailsDTO with isVerified set to false
        OTPDetailsDTO otpDetails = new OTPDetailsDTO(mobile, otp, false);

        try {
            // Convert OTPDetailsDTO to JSON string
            String redisValue = objectMapper.writeValueAsString(otpDetails);

            // Store in Redis with UUID as the key
            redisTemplate.opsForValue().set(uuid, redisValue);

            return "OTP Generated Successfully ";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Failed to generate OTP.";
        }
    }

    private String generateActualOTP() {
        return String.valueOf((int) (Math.random() * 900000 + 100000)); // Generate a 6-digit OTP
    }

    @Transactional
    public VerifyOTPResponseDTO verifyOTP(String otp) {
        Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            String storedValue = (String) redisTemplate.opsForValue().get(key);

            if (storedValue != null) {
                try {
                    OTPDetailsDTO otpDetails = objectMapper.readValue(storedValue, OTPDetailsDTO.class);
                    String mobile = otpDetails.getMobile();
                    String storedOTP = otpDetails.getOtp();

                    // Check if the OTP matches and if not already verified
                    if (storedOTP.equals(otp) && !otpDetails.isVerified()) {
                        // Verification successful
                        otpDetails.setVerified(true); // Set isVerified to true
                        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(otpDetails)); // Update in Redis

                        SuccessResponseDTO successResponse = new SuccessResponseDTO();
                        successResponse.setSuccessCode("200");
                        successResponse.setSuccessMsg("Verification successful");

                        SuccessResponseDTO.DataDTO data = new SuccessResponseDTO.DataDTO();
                        data.setRefId(key); // Use UUID as refId
                        successResponse.setData(data);

                        VerifyOTPResponseDTO response = new VerifyOTPResponseDTO();
                        response.setSuccess(successResponse);
                        response.setError(null);

                        return response; // Return verification success response
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        // Verification failed response
        VerifyOTPResponseDTO failureResponse = new VerifyOTPResponseDTO();
        failureResponse.setSuccess(null);
        failureResponse.setError("Verification failed");
        return failureResponse;
    }

    @Transactional
    public UserDetailsResponseDTO saveUserDetails(UserDetailsRequestDTO requestDTO) {
        // Retrieve mobile number and verification status from Redis using refId
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(requestDTO.getRefId());

        if (otpDetails == null) {
            return new UserDetailsResponseDTO("Mobile number not found or OTP not verified for refId", false);
        }

        // Check if user is verified before saving details
        if (!otpDetails.isVerified()) {
            return new UserDetailsResponseDTO("User is not verified, cannot save details", false);
        }

        // Create a new User and set the details
        User user = new User();
        user.setMobile(otpDetails.getMobile());
        user.setEmail(requestDTO.getEmail());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setDob(requestDTO.getDob());
        user.setRefNo(requestDTO.getRefId()); // Store refId in the user entity

        // Save the new user entity to the database
        userRepository.save(user);

        return new UserDetailsResponseDTO("User details saved successfully", true);
    }


    @Transactional
    public UserDetailsResponseDTO updateUserProfile(UpdateUserProfileRequestDTO requestDTO) {
        // Retrieve the OTPDetailsDTO from Redis using refId
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(requestDTO.getRefId());

        // Check if the user is verified before proceeding with the update
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new UserDetailsResponseDTO("User is not verified, cannot update details", false);
        }

        // Retrieve the user by refId
        User user = userRepository.findByRefNo(requestDTO.getRefId())
                .orElseThrow(() -> new RuntimeException("User not found with the provided refId"));

        // Update user details
        user.setEmail(requestDTO.getEmail());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());

        // Save the updated user entity to the database
        userRepository.save(user);

        return new UserDetailsResponseDTO("User profile updated successfully", true);
    }

    private OTPDetailsDTO getOTPDetailsFromRedis(String refId) {
        // Retrieve the value associated with refId in Redis
        String storedValue = (String) redisTemplate.opsForValue().get(refId);

        if (storedValue != null) {
            try {
                return objectMapper.readValue(storedValue, OTPDetailsDTO.class); // Return OTPDetailsDTO object
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null; // OTP details not found
    }


    @Transactional
    public GetUserResponseDTO getUserByEmail(String email) {
        // Retrieve user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the provided email"));

        // Ensure the user is verified
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(user.getRefNo());
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new GetUserResponseDTO("User is not verified", false, null, null, null, null);
        }

        // Return user details
        return new GetUserResponseDTO("User details retrieved successfully", true, user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobile());
    }

    @Transactional
    public GetUserResponseDTO getUserByMobile(String mobile) {
        // Retrieve user by mobile number
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found with the provided mobile number"));

        // Ensure the user is verified
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(user.getRefNo());
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new GetUserResponseDTO("User is not verified", false, null, null, null, null);
        }

        // Return user details
        return new GetUserResponseDTO("User details retrieved successfully", true, user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobile());
    }
}