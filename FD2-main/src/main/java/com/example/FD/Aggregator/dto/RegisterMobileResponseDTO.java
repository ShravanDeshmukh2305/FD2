package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class RegisterMobileResponseDTO {
    private SuccessData success;
    private Object error;

    @Data
    public static class SuccessData {
        private String successCode;
        private String successMsg;
        private DataDTO data;

        public SuccessData(String successCode, String successMsg, DataDTO data) {
            this.successCode = successCode;
            this.successMsg = successMsg;
            this.data = data;
        }
    }

    @Data
    public static class DataDTO {
        private String uuid;

        public DataDTO(String uuid) {
            this.uuid = uuid;
        }
    }

    public RegisterMobileResponseDTO(SuccessData success, Object error) {
        this.success = success;
        this.error = error;
    }
}
