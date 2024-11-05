package com.example.FD.Aggregator.dto;

import lombok.Data;

@Data
public class CreateMpinResponseDTO {
    private SuccessData success;
    private Object error;

    @Data
    public static class SuccessData {
        private String successCode;
        private String successMsg;
        private MpinData data;

        @Data
        public static class MpinData {
            private Long refId;
            private String userRefId;
            private String mpin;
            private Boolean isLocked;
            private Integer retry;
            private String createdAt;
            private String updatedAt;
        }
    }
}
