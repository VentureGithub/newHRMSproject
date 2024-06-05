package com.hrportal.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JobPostRequest {

	private String discription;
	private String jobRole;
	private String rolesResponsibilities;
	private String jobLocation;
	private String experinceRequired;
	private String typeOfJob;
}
