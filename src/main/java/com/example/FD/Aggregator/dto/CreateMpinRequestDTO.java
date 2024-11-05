package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class CreateMpinRequestDTO {
    private String userRefId;
    private String mpin;
    private Boolean isLocked;
    private Integer retry;
}

