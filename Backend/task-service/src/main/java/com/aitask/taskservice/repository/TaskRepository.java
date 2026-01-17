package com.aitask.taskservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aitask.taskservice.entity.Task;
import com.aitask.taskservice.enums.TaskPriority;
import com.aitask.taskservice.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ---------- Basic Fetch ----------

    Optional<Task> findByTaskId(Long taskId);

    List<Task> findByAssignedTo(Long userId);

    List<Task> findByCreatedBy(Long adminId);

    List<Task> findByProjectId(Long projectId);
    
    
    // ---------- Status Based ---------

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedToAndStatus(Long userId, TaskStatus status);

    List<Task> findByCreatedByAndStatus(Long adminId, TaskStatus status);

    // ---------- Priority Based ----------

    List<Task> findByAssignedToAndPriority(Long userId, TaskPriority priority);

    List<Task> findByPriority(TaskPriority priority);

    // ---------- Due Date / Overdue ----------

    List<Task> findByAssignedToAndDueDateBefore(Long userId, LocalDate date);

    List<Task> findByStatusAndDueDateBefore(TaskStatus status, LocalDate date);

    // ---------- AI / Analytics ----------

    List<Task> findByAiSuggestedPriorityTrue();

    List<Task> findByOverdueRiskGreaterThan(Double riskThreshold);

    // ---------- Dashboard / Admin ----------

    Long countByAssignedTo(Long userId);

    Long countByAssignedToAndStatus(Long userId, TaskStatus status);

    Long countByCreatedBy(Long adminId);

    // ---------- Sorting / Filtering ----------

    List<Task> findByAssignedToOrderByDueDateAsc(Long userId);

    List<Task> findByAssignedToOrderByCreatedAtDesc(Long userId);
}
