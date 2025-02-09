package com.identityprovider.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identityprovider.entity.Identity;
import com.identityprovider.entity.LoginOtp;
import com.identityprovider.helper.JwtHelper;
import com.identityprovider.model.request.JwtRequest;
import com.identityprovider.model.request.LoginRequest;
import com.identityprovider.model.response.JwtResponse;
import com.identityprovider.model.response.LoginSignUpResponse;
import com.identityprovider.repository.IdentityRepository;
import com.identityprovider.repository.LoginOtpRepository;
import com.identityprovider.service.IdentityService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Slf4j
public class IdentityController {

	private IdentityService identityService;

	private AuthenticationManager authenticationManager;

	private JwtHelper jwtHelper;

	private IdentityRepository identityRepo;

	private LoginOtpRepository loginOtpRepo;

	public IdentityController(IdentityService identityService, AuthenticationManager authenticationManager,
			JwtHelper jwtHelper, IdentityRepository identityRepo, LoginOtpRepository loginOtpRepo) {
		super();
		this.identityService = identityService;
		this.authenticationManager = authenticationManager;
		this.jwtHelper = jwtHelper;
		this.identityRepo = identityRepo;
		this.loginOtpRepo = loginOtpRepo;
	}

//	@PostMapping(value = "/login", consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginData) {

		try {
			LoginSignUpResponse responseData = this.identityService.loginUser(loginData);
			return new ResponseEntity<>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			log.error("ERROR | EMP NOT REGISTERED");
			return new ResponseEntity<>("ERROR :UNABLE TO REGISTER USER" + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/login-with-otp")
	public ResponseEntity<Object> loginWithOtpUser(@RequestBody JwtRequest jwtData) {

		boolean check = this.identityService.checkOtp(jwtData);

		LoginOtp otpResponse = this.loginOtpRepo.findByOtp(jwtData.getOtp());
		long noOfSeconds = otpResponse.getModifyAt().until(LocalDateTime.now(), ChronoUnit.SECONDS);
		long min = noOfSeconds / 60;

		if (!check) {
			log.warn("Warm! | otp have been wrong.");
			return new ResponseEntity<>("bad", HttpStatus.BAD_GATEWAY);
		} else if (min > 2) {
			log.warn("Warm! | otp expaired.!");
			return new ResponseEntity<>("bad", HttpStatus.BAD_GATEWAY);
		}

		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(jwtData.getEmail(), jwtData.getPassword()));

			if (authentication.isAuthenticated()) {
				UserDetails userDetailseData = this.identityService.loadUserByUsername(jwtData.getEmail());

				Identity identityData = this.identityRepo.findByEmail(jwtData.getEmail());
				String token = this.jwtHelper.generateToken(userDetailseData);

				String encodedToken = Base64.getEncoder().encodeToString(token.getBytes());

				JwtResponse jwtResponseData = new JwtResponse();
				jwtResponseData.setEmpCode(identityData.getEmpCode());
				jwtResponseData.setEmpNumber(identityData.getMobileNumber());
				jwtResponseData.setName(identityData.getName());
				jwtResponseData.setToken(encodedToken);
				jwtResponseData.setStatus("generate Token");
				log.info("MASSEGE | TOOKE GENERATED");
				return new ResponseEntity<>(jwtResponseData, HttpStatus.OK);
			} else {
				log.error("ERROR | UNABLE TO AUTHENTICATE");
				return new ResponseEntity<>("ERROR : NOT AUTHENTICATE", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			log.error("ERROR | INTERNAL SERVER ERROR");
			return new ResponseEntity<>("INTERNAL SERVER ERROR " + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

}
