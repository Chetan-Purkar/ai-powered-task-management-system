package com.aitask.taskservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aitask.taskservice.entity.Task;
import com.aitask.taskservice.enums.TaskPriority;
import com.aitask.taskservice.enums.TaskStatus;
import com.aitask.taskservice.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ---------------- CREATE TASK ----------------
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader(value = "X-Username", required = false) String username,
            @RequestHeader(value = "X-Roles", required = false) String role
    ) {
    	
    	System.out.println("Username " + username + " Role" + role);
        task.setCreatedBy(userId); // enforce creator
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(task, userId, role));
    }

    // ---------------- UPDATE TASK ----------------
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long taskId,
            @RequestBody Task task,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader(value = "X-Roles", required = false) String role
    ) {
    	
        return ResponseEntity.ok(
                taskService.updateTask(taskId, task, userId, role)
        );
    }
    
 // ---------------- CHANGE TASK Priority ----------------
    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<Void> updatePriority(
            @PathVariable Long taskId,
            @RequestParam TaskPriority priority,
            @RequestHeader("X-Roles") String role,
            @RequestHeader("X-User-Id") Long userId
    ) {
        taskService.updatePriority(taskId, priority, userId, role);
        return ResponseEntity.ok().build();
    }


    // ---------------- CHANGE TASK STATUS ----------------
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-Roles") String role
    ) {
        taskService.changeStatus(taskId, status, userId, role);
        
        return ResponseEntity.ok().build();
    }

    // ---------------- CURRENT USER TASKS ----------------
    @GetMapping("/me")
    public ResponseEntity<List<Task>> getMyTasks(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    // ---------------- TEST ENDPOINT ----------------
    @GetMapping("/test")
    public ResponseEntity<String> testTaskService() {
        return ResponseEntity.ok("✅ Task Service is WORKING");
    }
}
