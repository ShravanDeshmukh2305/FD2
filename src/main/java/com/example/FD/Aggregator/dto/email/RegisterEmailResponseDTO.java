package com.example.FD.Aggregator.dto.email;

import lombok.Data;

@Data
public class RegisterEmailResponseDTO {
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
        private String refNo;

        public DataDTO(String refNo) {
            this.refNo = refNo;
        }
    }

    public RegisterEmailResponseDTO(SuccessData success, Object error) {
        this.success = success;
        this.error = error;
    }
}
