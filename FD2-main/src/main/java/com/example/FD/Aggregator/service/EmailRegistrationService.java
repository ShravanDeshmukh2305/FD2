package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.config.AppConfig;
import com.example.FD.Aggregator.dto.email.*;
import com.example.FD.Aggregator.dto.email.EmailOTPDetailsDTO;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;


@Service
public class EmailRegistrationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppConfig appConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateEmailOTP(String email, String refNo) throws JsonProcessingException {
        String emailOTP = appConfig.isFakeotp() ? "123456" : generateActualOTP();
        String fetchMobileNo = (String) redisTemplate.opsForValue().get(refNo);

        EmailOTPDetailsDTO Mono = objectMapper.readValue(fetchMobileNo, EmailOTPDetailsDTO.class);

        EmailOTPDetailsDTO emailOtpDetails = new EmailOTPDetailsDTO(email, emailOTP, false);
        emailOtpDetails.setMobile(Mono.getMobile());

        try {

            // Deserialize each JSON object into a Map
            Map<String, Object> emailOtpDetailsMap = objectMapper.readValue(objectMapper.writeValueAsString(emailOtpDetails), Map.class);
            Map<String, Object> storedValueMap = objectMapper.readValue((String) redisTemplate.opsForValue().get(refNo), Map.class);

// Merge emailOtpDetailsMap into storedValueMap to create a single combined map
            storedValueMap.putAll(emailOtpDetailsMap);

// Serialize the combined map back to a JSON string
            String finalRedisValue = objectMapper.writeValueAsString(storedValueMap);

// Store the combined JSON in Redis
            redisTemplate.opsForValue().set(refNo, finalRedisValue);


            return "Email OTP Generated Successfully";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Failed to generate email OTP.";
        }
    }

    private String generateActualOTP() {
        return String.valueOf((int) (Math.random() * 900000 + 100000));
    }


    // Inject the AppConfig

    @Transactional
    public VerifyEmailOTPResponseDTO verifyEmailOTP(String emailOTP, String refNo, String preferredLang) {
        String storedValue = (String) redisTemplate.opsForValue().get(refNo);

        if (storedValue == null) {
            return buildFailureResponse("OTP details not found in Redis");
        }

        try {
            EmailOTPDetailsDTO emailOtpDetails = objectMapper.readValue(storedValue, EmailOTPDetailsDTO.class);
            String storedEmailOTP = emailOtpDetails.getEmailOTP();
            boolean isFakeOtpEnabled = appConfig.isFakeotp();  // Get fake OTP flag from config
            String fakeOtp = appConfig.getOtp();  // Fetch fake OTP value from the config

            // Check if OTP is correct or if fake OTP is allowed
            if ((storedEmailOTP.equals(emailOTP) || (isFakeOtpEnabled && emailOTP.equals(fakeOtp)))
                    && !emailOtpDetails.isEmailVerified()) {

                emailOtpDetails.setEmailVerified(true);
                redisTemplate.opsForValue().set(refNo, objectMapper.writeValueAsString(emailOtpDetails));

                User user = new User();
                user.setRefNo(refNo);
                user.setEmail(emailOtpDetails.getEmail());
                user.setPreferredLang(preferredLang);
                userRepository.save(user);

                String sessionToken = UUID.randomUUID().toString();

                UserRedisDTO userRedisDTO = new UserRedisDTO();
                userRedisDTO.setEmail(emailOtpDetails.getEmail());
                userRedisDTO.setMobile(emailOtpDetails.getMobile());
                userRedisDTO.setRefNo(refNo);
                redisTemplate.opsForValue().set(sessionToken, objectMapper.writeValueAsString(userRedisDTO));
                redisTemplate.expire(sessionToken, Duration.ofMinutes(60));

                // Prepare the success response
                VerifyEmailOTPResponseDTO response = new VerifyEmailOTPResponseDTO();
                VerifyEmailOTPResponseDTO.SuccessResponse success = new VerifyEmailOTPResponseDTO.SuccessResponse();
                success.setSuccessCode("200");
                success.setSuccessMsg("Email OTP verification successful");

                VerifyEmailOTPResponseDTO.SuccessResponse.Data data = new VerifyEmailOTPResponseDTO.SuccessResponse.Data();
                data.setRefId(refNo);
                data.setSessionToken(sessionToken);
                success.setData(data);

                response.setSuccess(success);
                response.setError(null);

                return response;
            } else {
                String errorMsg = storedEmailOTP.equals(emailOTP) ? "Email is already verified" : "Invalid OTP";
                return buildFailureResponse(errorMsg);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return buildFailureResponse("Error processing OTP verification: " + e.getMessage());
        }
    }

    private VerifyEmailOTPResponseDTO buildFailureResponse(String errorMsg) {
        VerifyEmailOTPResponseDTO failureResponse = new VerifyEmailOTPResponseDTO();
        failureResponse.setSuccess(null);
        failureResponse.setError(errorMsg);
        return failureResponse;
    }
}








/// session token (function )
/// Generate new uuid after data enter in DB
/// against new uuid save useremail, mobile, ref if,
// this new uuid is session token (60 min)
// in the response og verify email show session token to the user (UUID)


//guid, mpn, session token header, userrefid (redis),



