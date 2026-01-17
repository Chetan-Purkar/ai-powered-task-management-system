package com.aitask.taskservice.entity;

import java.time.LocalDateTime;

import com.aitask.taskservice.enums.TaskAction;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- References ----------

    private Long taskId; // Task ID
    private Long userId; // User who performed the action

    // ---------- Action Type ----------

    @Enumerated(EnumType.STRING)
    private TaskAction action; // CREATED / UPDATED / COMPLETED

    // ---------- Timestamp ----------

    private LocalDateTime timestamp;

    // ---------- Auto Timestamp ----------

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
