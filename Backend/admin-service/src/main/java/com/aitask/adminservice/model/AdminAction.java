package com.aitask.adminservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- Admin Info ----------

    @Column(nullable = false)
    private Long adminId; // from JWT

    @Column(length = 100, nullable = false)
    private String adminUsername;

    // ---------- Action Details ----------

    @Column(length = 50, nullable = false)
    private String actionType;

    @Column(length = 100)
    private String targetType; // USER, TASK, PROJECT, REPORT

    private Long targetId;

    @Column(length = 1000)
    private String description;

    // ---------- Request Metadata ----------

    @Column(length = 45)
    private String ipAddress;

    @Column(length = 255)
    private String userAgent;

    // ---------- Timestamp ----------

    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ---------- Lifecycle ----------

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
