package com.hrportal.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CandidateDetailsDTO {

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
}
