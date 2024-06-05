package com.admin.model.response;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SignUpResponse {

	private String name;
	private String email;
	private String mobileNumber;
	private String password;
	private Long empNumber;
	private String empCode;
	private String status;
	private LocalDateTime createdAt;

}
