package com.aitask.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aitask.taskservice.entity.TaskActivity;

public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long> {

    List<TaskActivity> findByTaskIdOrderByTimestampDesc(Long taskId);
}
