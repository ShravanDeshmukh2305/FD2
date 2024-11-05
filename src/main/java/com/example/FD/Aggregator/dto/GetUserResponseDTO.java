package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class GetUserResponseDTO {
    private SuccessData success;
    private Object error;

    @Data
    public static class SuccessData {
        private String successCode;
        private String successMsg;
        private UserData data;

        public SuccessData(String successCode, String successMsg, UserData data) {
            this.successCode = successCode;
            this.successMsg = successMsg;
            this.data = data;
        }
    }

    @Data
    public static class UserData {
        private String email;
        private String firstName;
        private String lastName;
        private String mobile;

        public UserData(String email, String firstName, String lastName, String mobile) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mobile = mobile;
        }
    }

    public GetUserResponseDTO(SuccessData success, Object error) {
        this.success = success;
        this.error = error;
    }
}
