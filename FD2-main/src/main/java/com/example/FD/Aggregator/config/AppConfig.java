//package com.example.FD.Aggregator.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "spring")
//public class AppConfig {
//    private boolean fakeotp;
//
//    // Getters and setters
//    public boolean isFakeotp() {
//        return fakeotp;
//    }
//
//    public void setFakeotp(boolean fakeotp) {
//        this.fakeotp = fakeotp;
//    }
//}
//


package com.example.FD.Aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private boolean fakeotp;
    private String otp;  // Add OTP configuration

    // Getters and setters
    public boolean isFakeotp() {
        return fakeotp;
    }

    public void setFakeotp(boolean fakeotp) {
        this.fakeotp = fakeotp;
    }

    public String getOtp() {
        return otp;  // Return the OTP value
    }

    public void setOtp(String otp) {
        this.otp = otp;  // Set the OTP value
    }
}
