package com.identityprovider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.identityprovider.service.CheckPointService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/check-point")
@CrossOrigin(origins = "*")
@Slf4j
public class CheckPointController {

	private CheckPointService checkPointService;

	public CheckPointController(CheckPointService checkPointService) {
		super();
		this.checkPointService = checkPointService;
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		this.checkPointService.validateToken(token);

		return "Token is valid";
	}

	@GetMapping("/validate/role")
	public List<String> validateTokenRole(@RequestParam("token") String token) {
		return this.checkPointService.getRoles(token);
	}

	@GetMapping("/kkk")
	public String kkk() {
		return "ok";
	}

}
