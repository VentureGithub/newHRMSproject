package com.hrportal.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CandidatePassRequest {

	private String candidateId;
	private String email;
	private String password;
	private String confirmPassword;
	private int otp;

}
