package com.identityprovider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.identityprovider.entity.Identity;
import com.identityprovider.model.request.SignUpRequest;
import com.identityprovider.model.response.SignUpResponse;
import com.identityprovider.service.IdentityService;
import com.identityprovider.service.adminAccessService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/access")
@Slf4j
public class adminAccessController {

	private adminAccessService adminAccess;

	private IdentityService identityService;

	public adminAccessController(adminAccessService adminAccess, IdentityService identityService) {
		super();
		this.adminAccess = adminAccess;
		this.identityService = identityService;
	}

	@PostMapping("/admin/create-hr-credential")
	public ResponseEntity<Object> createCredentialOfHr(@RequestBody SignUpRequest signUpData) {
		try {
			signUpData.setRole("HR");
			SignUpResponse responseData = this.identityService.createCredential(signUpData);
			log.info("MEESSAGE | REGISTER CREDENTIAL SUCCESSFULLY");
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR | EMP NOT REGISTERED");
			return new ResponseEntity<>("ERROR :UNABLE TO REGISTER USER" + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/hr/create-emp-credential")
	public ResponseEntity<Object> createCredentialOfEmp(@RequestBody SignUpRequest signUpData) {
		try {
			signUpData.setRole("EMP");
			SignUpResponse responseData = this.identityService.createCredential(signUpData);
			log.info("MEESSAGE | REGISTER CREDENTIAL SUCCESSFULLY");
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR | EMP NOT REGISTERED");
			return new ResponseEntity<>("ERROR :UNABLE TO REGISTER USER" + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/freeze")
	public ResponseEntity<Object> freezeAccount(@RequestParam("empCode") Long empCode) {
		try {
			Identity responseData = this.adminAccess.freeshAccontOfUser(empCode);
			return new ResponseEntity<>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			log.error("ERROR | NOT FREEZE ACCOUNT");
			return new ResponseEntity<>("ERROR :UNABLE TO FREEZE EMP ACCOUNT" + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}

	}

}
