package com.aitask.projectservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "projects")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;
	
	
	private String name;
	private String description;
	
	
	private Long ownerId; // userId
	
	
	@ElementCollection
	private List<Long> members; // userIds
	
	
	private String status; // ACTIVE / ARCHIVED
	
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	private LocalDateTime createdAt;
}