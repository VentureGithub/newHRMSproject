package com.identityprovider.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginSignUpResponse {

	private String name;
	private int otp;
	private String ststus;

}
