package com.example.FD.Aggregator.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateMpinRequestDTO {
    private String guid;
    private String mpin;
    private String osType;
    private String osVersion;
    private String deviceBrand;
    private String deviceModel;
    private String appVersion;
    private LocalDate loginDate;
}

