package com.example.FD.Aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring")
public class AppConfig {
    private boolean fakeotp;

    // Getters and setters
    public boolean isFakeotp() {
        return fakeotp;
    }

    public void setFakeotp(boolean fakeotp) {
        this.fakeotp = fakeotp;
    }
}

