package com.aitask.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    // ---------- Profile Info ----------

    @Column(length = 100)
    private String name;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String profileImage;

    @Column(length = 500)
    private String bio;

    // ---------- Preferences ----------

    @Column(length = 255)
    private String notificationSettings;

    @Column(length = 50)
    private String timezone;

    @Column(length = 20)
    private String language;

    @Column(length = 20)
    private String theme;

    // ---------- AI & Analytics ----------

    private Integer productivityScore;

    @Column(length = 100)
    private String focusPattern;

    @Column(length = 50)
    private String burnoutRisk;

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
