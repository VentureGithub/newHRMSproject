package com.hrportal.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class CandidatePassResponse {

	private String candidateId;
	private String email;
	private String status;

}
