package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class SuccessResponseDTO {
    private String successCode;
    private String successMsg;
    private DataDTO data;

    @Data
    public static class DataDTO {
        private String refId;
    }
}

