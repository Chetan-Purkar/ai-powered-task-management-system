package com.aitask.adminservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProfile {

    @Id
    @Column(name = "admin_id")
    private Long adminId; // Shared userId from auth-service (ROLE_ADMIN)

    // ---------- Profile Info ----------

    @Column(length = 100)
    private String name;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String profileImage;

    @Column(length = 500)
    private String bio;

    // ---------- Admin Preferences ----------

    @Column(length = 50)
    private String timezone;

    @Column(length = 20)
    private String language;

    @Column(length = 20)
    private String theme;

    // ---------- Admin Metadata ----------

    @Column(length = 50)
    private String accessLevel; // SUPER_ADMIN, MODERATOR, SUPPORT

    private LocalDateTime lastActive;

    // ---------- Timestamps ----------

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ---------- Lifecycle ----------

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
    }
}
