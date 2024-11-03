package com.example.FD.Aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private SuccessResponse<T> success;
    private ErrorResponse error;

    @Data
    @AllArgsConstructor
    public static class SuccessResponse<T> {
        private String successCode;
        private String successMsg;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private String errorCode;
        private String errorMsg;
    }

    @Data
    @AllArgsConstructor
    public static class TokenData {
        private String token;
    }
}

