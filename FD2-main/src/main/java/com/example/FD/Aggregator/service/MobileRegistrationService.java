package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.config.AppConfig;
import com.example.FD.Aggregator.dto.*;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.time.Duration;
import java.util.UUID;

@Slf4j
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
    public VerifyOTPResponseDTO verifyOTP(String otp, String refNo) {
        String storedValue = (String) redisTemplate.opsForValue().get(refNo);

        if (storedValue != null) {
            try {
                OTPDetailsDTO otpDetails = objectMapper.readValue(storedValue, OTPDetailsDTO.class);
                String storedOTP = otpDetails.getOtp();

                boolean isFakeOtpEnabled = appConfig.isFakeotp();
                String fakeOtp = appConfig.getOtp();

                // Check if the OTP matches and if it has not been verified
                if ((storedOTP.equals(otp) || (isFakeOtpEnabled && otp.equals(fakeOtp))) && !otpDetails.isVerified()) {
                    // Verification successful
                    otpDetails.setVerified(true); // Set isVerified to true
                    redisTemplate.opsForValue().set(refNo, objectMapper.writeValueAsString(otpDetails)); // Update in Redis

                    SuccessResponseDTO successResponse = new SuccessResponseDTO();
                    successResponse.setSuccessCode("200");
                    successResponse.setSuccessMsg("Verification successful");

                    SuccessResponseDTO.DataDTO data = new SuccessResponseDTO.DataDTO();
                    data.setRefId(refNo); // Use refNo as refId
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

        // Verification failed response
        VerifyOTPResponseDTO failureResponse = new VerifyOTPResponseDTO();
        failureResponse.setSuccess(null);
        failureResponse.setError("Verification failed");
        return failureResponse;
    }



    @Transactional
    public UserDetailsResponseDTO saveUserDetails(UserDetailsRequestDTO requestDTO) {
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(requestDTO.getRefId());

        if (otpDetails == null) {
            return new UserDetailsResponseDTO(null, "Mobile number not found or OTP not verified for refId");
        }

        if (!otpDetails.isVerified()) {
            return new UserDetailsResponseDTO(null, "User is not verified, cannot save details");
        }

        User user = new User();
        user.setMobile(otpDetails.getMobile());
        user.setEmail(requestDTO.getEmail());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setDob(requestDTO.getDob());
        user.setRefNo(requestDTO.getRefId());

        // Save the new user entity to the database
        user = userRepository.save(user);

        // Fetch the user again to ensure createdDate and updatedDate are set
        user = userRepository.findByRefNo(user.getRefNo()).orElse(user);

        // Prepare response
        UserDetailsResponseDTO.DataDTO dataDTO = new UserDetailsResponseDTO.DataDTO(
                user.getRefNo(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMobile(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );

        UserDetailsResponseDTO.SuccessData successData = new UserDetailsResponseDTO.SuccessData("200", "User details saved successfully", dataDTO);
        return new UserDetailsResponseDTO(successData, null);
    }






    @Transactional
    public UpdateUserProfileResponseDTO updateUserProfile(UpdateUserProfileRequestDTO requestDTO) {
        // Retrieve the OTPDetailsDTO from Redis using refId
        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(requestDTO.getRefId());

        // Check if the user is verified before proceeding with the update
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new UpdateUserProfileResponseDTO(null, "User is not verified, cannot update details");
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

        // Construct the data for the response
        UpdateUserProfileResponseDTO.DataDTO dataDTO = new UpdateUserProfileResponseDTO.DataDTO(
                requestDTO.getRefId(),
                requestDTO.getEmail(),
                requestDTO.getFirstName(),
                requestDTO.getLastName()
        );

        // Create success response data
        UpdateUserProfileResponseDTO.SuccessData successData = new UpdateUserProfileResponseDTO.SuccessData(
                "200",
                "User profile updated successfully",
                dataDTO
        );

        // Return the success response
        return new UpdateUserProfileResponseDTO(successData, null);
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the provided email"));

        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(user.getRefNo());
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new GetUserResponseDTO(null, "User is not verified");
        }

        GetUserResponseDTO.UserData userData = new GetUserResponseDTO.UserData(
                user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobile());

        GetUserResponseDTO.SuccessData successData = new GetUserResponseDTO.SuccessData(
                "200", "User details retrieved successfully", userData);

        return new GetUserResponseDTO(successData, null);
    }



    @Transactional
    public GetUserResponseDTO getUserByMobile(String mobile) {
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found with the provided mobile number"));

        OTPDetailsDTO otpDetails = getOTPDetailsFromRedis(user.getRefNo());
        if (otpDetails == null || !otpDetails.isVerified()) {
            return new GetUserResponseDTO(null, "User is not verified");
        }

        GetUserResponseDTO.UserData userData = new GetUserResponseDTO.UserData(
                user.getEmail(), user.getFirstName(), user.getLastName(), user.getMobile());

        GetUserResponseDTO.SuccessData successData = new GetUserResponseDTO.SuccessData(
                "200", "User details retrieved successfully", userData);

        return new GetUserResponseDTO(successData, null);
    }
}





