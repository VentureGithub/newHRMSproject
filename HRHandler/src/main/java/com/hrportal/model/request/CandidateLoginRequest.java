package com.hrportal.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CandidateLoginRequest {

	private String email;
	private String password;

}
