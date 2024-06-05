package com.identityprovider.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JwtResponse {

	private String name;
	private String empCode;
	private String empNumber;
	private String token;
	private String status;

}
