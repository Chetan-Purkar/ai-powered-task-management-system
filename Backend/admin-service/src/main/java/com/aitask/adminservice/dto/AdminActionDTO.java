package com.aitask.adminservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminActionDTO {

    private Long id;

    // ---------- Admin Info ----------

    private Long adminId;
    private String adminUsername;

    // ---------- Action Details ----------

    private String actionType;
    private String targetType;
    private Long targetId;
    private String description;

    // ---------- Request Metadata ----------

    private String ipAddress;
    private String userAgent;

    // ---------- Timestamp ----------

    private LocalDateTime createdAt;
}
