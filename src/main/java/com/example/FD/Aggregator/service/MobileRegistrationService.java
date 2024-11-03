//package com.example.FD.Aggregator.service;
//
//import com.example.FD.Aggregator.entity.User;
//import com.example.FD.Aggregator.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//public class MobileRegistrationService {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public String generateOTP(String mobile, boolean fakeOTP) {
//        String uuid = UUID.randomUUID().toString();
//        String otp = fakeOTP ? "123456" : generateActualOTP();
//
//        // Store OTP in Redis
//        redisTemplate.opsForValue().set(uuid, mobile + "," + otp);
//        return "Generated OTP: " + otp + " for UUID: " + uuid;
//    }
//
//    private String generateActualOTP() {
//        return String.valueOf((int) (Math.random() * 900000 + 100000)); // Generate a 6-digit OTP
//    }
//
//    @Transactional
//    public boolean verifyOTPAndSave(String uuid, String otp) {
//        String storedValue = (String) redisTemplate.opsForValue().get(uuid);
//        if (storedValue != null) {
//            String[] parts = storedValue.split(",");
//            String mobile = parts[0];
//            String storedOTP = parts[1];
//
//            if (storedOTP.equals(otp)) {
//                // OTP verification successful, save user to DB
//                User user = new User();
//                user.setMobile(mobile);
//                user.setRefNo(uuid);
//                userRepository.save(user);
//                return true;
//            }
//        }
//        return false;
//    }
//}


package com.example.FD.Aggregator.service;

import com.example.FD.Aggregator.config.AppConfig; // Import the AppConfig class
import com.example.FD.Aggregator.dto.SuccessResponseDTO;
import com.example.FD.Aggregator.dto.UserDetailsRequestDTO;
import com.example.FD.Aggregator.dto.UserDetailsResponseDTO;
import com.example.FD.Aggregator.dto.VerifyOTPResponseDTO;
import com.example.FD.Aggregator.entity.User;
import com.example.FD.Aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class MobileRegistrationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppConfig appConfig; // Autowire the AppConfig class

    public String generateOTP(String mobile) { // Remove the fakeOTP parameter
        String uuid = UUID.randomUUID().toString();
        String otp = appConfig.isFakeotp() ? "123456" : generateActualOTP(); // Use the value from the configuration

        // Store OTP in Redis
        redisTemplate.opsForValue().set(uuid, mobile + "," + otp);
        return "Generated OTP: " + otp + " for UUID: " + uuid;
    }

    private String generateActualOTP() {
        return String.valueOf((int) (Math.random() * 900000 + 100000)); // Generate a 6-digit OTP
    }

    @Transactional
    public VerifyOTPResponseDTO verifyOTP(String otp) {
        Set<String> keys = redisTemplate.keys("*"); // Fetch all keys (this can be optimized)

        for (String key : keys) {
            String storedValue = (String) redisTemplate.opsForValue().get(key);
            String[] parts = storedValue.split(",");
            String mobile = parts[0];
            String storedOTP = parts[1];

            if (storedOTP.equals(otp)) {
                // OTP verification successful, save user to DB
                User user = new User();
                user.setMobile(mobile);
                String refId = UUID.randomUUID().toString(); // Generate a new reference ID
                user.setRefNo(refId); // Save the generated refId to the user entity
                userRepository.save(user);
                redisTemplate.delete(key); // Optionally remove the OTP from Redis after verification

                // Create success response
                SuccessResponseDTO successResponse = new SuccessResponseDTO();
                successResponse.setSuccessCode("200"); // Set a success code
                successResponse.setSuccessMsg("Verification successful ");

                SuccessResponseDTO.DataDTO data = new SuccessResponseDTO.DataDTO();
                data.setRefId(refId); // Set the refId in the data DTO

                successResponse.setData(data);

                VerifyOTPResponseDTO response = new VerifyOTPResponseDTO();
                response.setSuccess(successResponse);
                response.setError(null); // No error

                return response; // Return the structured response
            }
        }

        // Return response indicating failure
        return null; // OTP verification failed
    }




@Transactional
public UserDetailsResponseDTO saveUserDetails(UserDetailsRequestDTO requestDTO) {
    // Retrieve mobile number from Redis using refId
    String mobile = getMobileFromRedis(requestDTO.getRefId());

    if (mobile == null) {
        return new UserDetailsResponseDTO("Mobile number not found for refId", false);
    }

    // Create a new User entity and set its properties
    User user = new User();
    user.setMobile(mobile);
    user.setRefNo(requestDTO.getRefId());
    user.setEmail(requestDTO.getEmail());
    user.setFirstName(requestDTO.getFirstName());
    user.setLastName(requestDTO.getLastName());
    user.setDob(requestDTO.getDob());

    // Save the user entity to the database
    userRepository.save(user);

    // Return success response
    return new UserDetailsResponseDTO("User details saved successfully", true);
}

private String getMobileFromRedis(String refId) {
    Set<String> keys = redisTemplate.keys("*"); // Fetch all keys to find the mobile number

    for (String key : keys) {
        Object storedValue = redisTemplate.opsForValue().get(key);
        if (storedValue == null){
            continue;
        }
        String[] parts = storedValue.toString().split(",");


        if (parts == null || parts.length == 0){
            continue;
        }

        if (key.equals(refId)) {
            return parts[0]; // Return the mobile number associated with this refId
        }
    }
    return null; // Mobile number not found
}
}


