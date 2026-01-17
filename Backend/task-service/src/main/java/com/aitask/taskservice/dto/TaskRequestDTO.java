package com.aitask.taskservice.dto;

import java.time.LocalDate;

import com.aitask.taskservice.enums.TaskPriority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {

    private String title;
    private String description;

    private TaskPriority priority;   // optional (AI may decide)
    private LocalDate dueDate;

    private Integer estimatedTime;    // optional
}
