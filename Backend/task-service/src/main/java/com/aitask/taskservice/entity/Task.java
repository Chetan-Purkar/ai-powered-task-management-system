package com.aitask.taskservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;



import com.aitask.taskservice.enums.TaskPriority;
import com.aitask.taskservice.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Index;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	    name = "tasks",
	    indexes = {
	        @Index(name = "idx_task_assigned_to", columnList = "assignedTo"),
	        @Index(name = "idx_task_project", columnList = "projectId"),
	        @Index(name = "idx_task_status", columnList = "status")
	    }
	)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    // ---------- Task Info ----------

    private String title;
    private String description;
    
    private Long projectId;

    // ---------- Status & Priority ----------

    @Enumerated(EnumType.STRING)
    private TaskStatus status; // TODO / IN_PROGRESS / DONE

    @Enumerated(EnumType.STRING)
    private TaskPriority priority; // LOW / MEDIUM / HIGH

    // ---------- AI Fields ----------

    private Boolean aiSuggestedPriority; // true if AI assigned priority
    private Double overdueRisk;           // probability (0.0 - 1.0)

    // ---------- Audit Fields ----------

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---------- User Mapping (Microservice Safe) ----------

    private Long assignedTo; // userId
    private Long createdBy;  // userId

    // ---------- Time Management ----------

    private LocalDate dueDate;
    private Integer estimatedTime; // minutes (AI predicted)
    private Integer actualTime;    // minutes

    // ---------- JPA Lifecycle Hooks ----------

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
