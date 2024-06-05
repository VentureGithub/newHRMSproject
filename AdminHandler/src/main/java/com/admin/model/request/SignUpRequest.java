package com.admin.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SignUpRequest {

	private String name;
	private String email;
	private String mobileNumber;
	private String password;
	private String role;

}
