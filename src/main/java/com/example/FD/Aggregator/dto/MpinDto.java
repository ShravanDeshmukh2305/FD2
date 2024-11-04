package com.example.FD.Aggregator.dto;


import lombok.Data;
import java.time.LocalDateTime;
@Data
public class MpinDto {
    private Long refId;
    private Long userRefId;
    private String uuid;
    private String mpin;
    private Boolean isLocked;
    private Integer retry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
