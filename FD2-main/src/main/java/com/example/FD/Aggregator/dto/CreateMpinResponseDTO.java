package com.example.FD.Aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateMpinResponseDTO {
    private SuccessData success;
    private Object error;

    public CreateMpinResponseDTO(SuccessData success, Object error) {
        this.success = success;
        this.error = error;
    }

    public CreateMpinResponseDTO() {
        this.success = null;
        this.error = null;
    }

    @Data
    public static class SuccessData {
        private String successCode;
        private String successMsg;
        private MpinData data;

        @Data
        public static class MpinData {
            private Long refId;
            private String guid;
            private String userRefId;
            private String mpin;
            private Boolean isLocked;
            private Integer retry;
            private String createdAt;
            private String updatedAt;
            private String osType;
            private String osVersion;
            private String deviceBrand;
            private String deviceModel;
            private String appVersion;
            private String loginDate;
        }
    }
}

