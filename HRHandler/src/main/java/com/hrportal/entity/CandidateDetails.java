package com.hrportal.entity;

import java.time.LocalDate;
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
public class CandidateDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String candidateId;
	private String name;
	private String email;
	private String designation;
	private LocalDate dateOfBirth;
	private String address;
	private String overAllExperince;
	private String releventExperince;
	private String rating;
	private String qulification;
	private String yearOfPassing;
	private String techStack;
	private String currentCTC;
	private String expectationCTC;
	private String noticePeriod;
	private boolean relocate;
	private LocalDateTime createdAt;
	private LocalDateTime modifyAt;
}
