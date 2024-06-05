package com.user.getway.config;

import org.springframework.stereotype.Component;

@Component
public class RoleModel {

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public RoleModel(String role) {
		super();
		this.role = role;
	}

	public RoleModel() {
		super();
	}

	@Override
	public String toString() {
		return "RoleModel [role=" + role + "]";
	}

}
