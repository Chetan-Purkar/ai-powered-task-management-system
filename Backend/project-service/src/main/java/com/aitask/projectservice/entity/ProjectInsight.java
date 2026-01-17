package com.aitask.projectservice.entity;

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
@Table(name = "project_insights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectInsight {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private Long projectId;
private Double progressPrediction;
private Double delayRisk;
private Integer teamLoad;
}