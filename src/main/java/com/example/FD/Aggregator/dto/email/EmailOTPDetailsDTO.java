package com.example.FD.Aggregator.dto.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailOTPDetailsDTO {

    private String email;
    private String emailOTP;
    private boolean emailVerified;
    private String mobile;

    // Explicitly define the constructor
    public EmailOTPDetailsDTO(String email, String emailOTP, boolean emailVerified) {
        this.email = email;
        this.emailOTP = emailOTP;
        this.emailVerified = emailVerified;
    }
}





//
//package com.example.FD.Aggregator.dto.email;
//
//import lombok.Getter;
//import lombok.Setter;
//
//public class EmailOTPDetailsDTO {
//    // Getters and setters
//    @Getter
//    @Setter
//    private String email;
//    @Getter
//    @Setter
//    private String emailOTP;
//    private boolean isEmailVerified;
//    // Setter for mobile
//    // Getter for mobile
//    @Setter
//    @Getter
//    private String mobile;  // Add the mobile field
//
//    public EmailOTPDetailsDTO(String email, String emailOTP, boolean b) {
//
//    }
//
//    public boolean isEmailVerified() {
//        return isEmailVerified;
//    }
//
//    public void setEmailVerified(boolean emailVerified) {
//        isEmailVerified = emailVerified;
//    }
//
//}
