package com.aitask.taskservice.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitask.taskservice.entity.Task;
import com.aitask.taskservice.entity.TaskActivity;
import com.aitask.taskservice.enums.TaskAction;
import com.aitask.taskservice.enums.TaskPriority;
import com.aitask.taskservice.enums.TaskStatus;
import com.aitask.taskservice.repository.TaskActivityRepository;
import com.aitask.taskservice.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskActivityRepository activityRepository;

    // ---------------- CREATE TASK (ADMIN ONLY) ----------------
    @Transactional
    public Task createTask(Task task, Long userId, String role) {
    	System.out.println("role = " + role);
        if (!role.contains("ADMIN")) {
            throw new AccessDeniedException("Only ADMIN can create tasks");
        }
        

        task.setStatus(TaskStatus.TODO);
        task.setCreatedBy(userId);

        Task savedTask = taskRepository.save(task);

        logActivity(savedTask.getTaskId(), userId, TaskAction.CREATED);
        return savedTask;
    }

    // ---------------- UPDATE TASK (ADMIN ONLY) ----------------
    @Transactional
    public Task updateTask(Long taskId, Task updatedTask, Long userId, String role) {
        if (!role.contains("ADMIN")) {
            throw new AccessDeniedException("Only ADMIN can update tasks");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());

        logActivity(taskId, userId, TaskAction.UPDATED);
        return taskRepository.save(task);
    }

    // ---------------- UPDATE PRIORITY (ADMIN ONLY) ----------------
    @Transactional
    public void updatePriority(Long taskId, TaskPriority priority, Long userId, String role) {
        if (!role.contains("ADMIN")) {
            throw new AccessDeniedException("Only ADMIN can update task priority");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        task.setPriority(priority);

        logActivity(taskId, userId, TaskAction.UPDATED);
    }

    // ---------------- UPDATE STATUS (ADMIN + USER) ----------------
    @Transactional
    public void changeStatus(Long taskId, TaskStatus status, Long userId, String role) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        // USER can update only assigned task
        if (role.contains("USER") && !task.getAssignedTo().equals(userId)) {
            throw new AccessDeniedException("User can update only assigned task status");
        }

        task.setStatus(status);

        if (status == TaskStatus.DONE) {
            logActivity(taskId, userId, TaskAction.COMPLETED);
        } else {
            logActivity(taskId, userId, TaskAction.STATUS_CHANGED);
        }
    }

    // ---------------- GET USER TASKS ----------------
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByAssignedTo(userId);
    }

    // ---------------- ACTIVITY LOGGER ----------------
    private void logActivity(Long taskId, Long userId, TaskAction action) {
        TaskActivity activity = TaskActivity.builder()
                .taskId(taskId)
                .userId(userId)
                .action(action)
                .build();

        activityRepository.save(activity);
    }
}
