package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class UpdateUserProfileResponseDTO {
    private SuccessData success;
    private Object error;

    public UpdateUserProfileResponseDTO(SuccessData success, Object error) {
        this.success = success;
        this.error = error;
    }

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
        private String refId;
        private String email;
        private String firstName;
        private String lastName;

        public DataDTO(String refId, String email, String firstName, String lastName) {
            this.refId = refId;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
