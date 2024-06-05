package com.identityprovider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.identityprovider.helper.JwtHelper;
import com.identityprovider.repository.IdentityRepository;

@ComponentScan
@Service
public class CheckPointService {

	private JwtHelper jwtHelper;

	private IdentityRepository identityService;

	public CheckPointService(JwtHelper jwtHelper, IdentityRepository identityService) {
		super();
		this.jwtHelper = jwtHelper;
		this.identityService = identityService;
	}

	public void validateToken(String token) {
		jwtHelper.validateToken(token);
	}

	public List<String> getRoles(String token) {

		String userName = this.jwtHelper.getUsernameFromToken(token);

		UserDetails data = this.identityService.findByEmail(userName);
		List<String> userRoles = new ArrayList<>();
		userRoles = data.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		return userRoles;
	}

}
