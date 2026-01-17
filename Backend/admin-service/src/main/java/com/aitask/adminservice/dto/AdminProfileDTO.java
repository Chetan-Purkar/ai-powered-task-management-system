package com.aitask.adminservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProfileDTO {

    private Long adminId;

    // ---------- Profile Info ----------

    private String name;
    private String phoneNumber;
    private String profileImage;
    private String bio;

    // ---------- Preferences ----------

    private String timezone;
    private String language;
    private String theme;

    // ---------- Admin Metadata ----------

    private String accessLevel;

    // ---------- Audit ----------

    private LocalDateTime lastActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
