package com.example.FD.Aggregator.dto.email;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyEmailOTPResponseDTO {
    // Getters and setters
    private SuccessResponse success;
    private String error;

    public void setSuccess(SuccessResponse success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }

    // Inner class for SuccessResponse structure
    public static class SuccessResponse {
        private String successCode;
        private String successMsg;
        private Data data;

        // Getters and setters
        public String getSuccessCode() {
            return successCode;
        }

        public void setSuccessCode(String successCode) {
            this.successCode = successCode;
        }

        public String getSuccessMsg() {
            return successMsg;
        }

        public void setSuccessMsg(String successMsg) {
            this.successMsg = successMsg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        // Inner class for Data structure
        public static class Data {
            private String refId;
            private String sessionToken;

            // Getters and setters
            public String getRefId() {
                return refId;
            }

            public void setRefId(String refId) {
                this.refId = refId;
            }

            public String getSessionToken() {
                return sessionToken;
            }

            public void setSessionToken(String sessionToken) {
                this.sessionToken = sessionToken;
            }
        }
    }
}

