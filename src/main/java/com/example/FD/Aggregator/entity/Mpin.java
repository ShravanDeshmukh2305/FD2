package com.example.FD.Aggregator.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mpin")
@Data
public class Mpin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ref_id", nullable = false, updatable = false)
    private Long refId;

    @ManyToOne
    @JoinColumn(name = "user_ref_id", referencedColumnName = "ref_id", nullable = false)
    private User user;

    @Column(name = "guid", nullable = false)
    private String guid;

    @Column(name = "mpin", nullable = false)
    private String mpin;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false; // Default value

    @Column(name = "retry", nullable = false)
    private Integer retry = 0; // Default value

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // New fields for device information
    @Column(name = "os_type")
    private String osType;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "device_brand")
    private String deviceBrand;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "login_date")
    private LocalDate loginDate;
}
