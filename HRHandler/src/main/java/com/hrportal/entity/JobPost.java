package com.hrportal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class JobPost {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String jobId;
	private String discription;
	private String jobRole;
	private String rolesResponsibilities;
	private String jobLocation;
	private String experinceRequired;
	private String typeOfJob;
	private LocalDateTime createdAt;
	private LocalDateTime modifyAt;

}
