package com.hrportal.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrportal.entity.CandidateCredential;
import com.hrportal.entity.CandidateOtp;
import com.hrportal.model.request.CandidateLoginRequest;
import com.hrportal.model.request.CandidatePassRequest;
import com.hrportal.model.response.CandidatePassResponse;
import com.hrportal.repository.CandidateOtpRepository;
import com.hrportal.service.CandidateService;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@RestController
@RequestMapping("/Candidate")
@Slf4j
@CrossOrigin(origins = "*")
public class CandidateController {

	private CandidateService candidateService;

	private CandidateOtpRepository candidateOtpData;

	public CandidateController(CandidateService candidateService, CandidateOtpRepository candidateOtpData) {
		super();
		this.candidateService = candidateService;
		this.candidateOtpData = candidateOtpData;
	}

	@PostMapping("/add-candidate")
	public ResponseEntity<Object> addCandidate(@RequestParam("Email") String email) {

		try {
			CandidateCredential candidateResponse = this.candidateService.addCondidateResume(email);
			if (candidateResponse == null) {
				log.error("ERROR: SERVICE LYEAR ERROR");
				throw new IllegalArgumentException("ERROR | WE NOT EBALE TO  FIND  DAAT ON SERVICE LYEAR");
			}

			log.info("MESSAGE: CANDIDATE SAVE");
			return new ResponseEntity<>("MESSAGE | CANDIDATE SAVE SUCCESSFULLY IN OUR DATABASE", HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR: DATA BASE LYEAR ERRROR ");
			return new ResponseEntity<>("ERROR | CANDIDATE NOT SAVE" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login-candidate/with-email")
	public ResponseEntity<Object> loginCandidate(@RequestBody CandidateLoginRequest loginData) {

		if ("welcom@123".equals(loginData.getPassword())) {
			return new ResponseEntity<>("please reset your password", HttpStatus.SERVICE_UNAVAILABLE);
		}

		try {
			CandidateOtp candidateResponse = this.candidateService.loginWithEmailAndPass(loginData);
			if (candidateResponse == null) {
				log.error("ERROR: SERVICE LYEAR ERROR");
				throw new IllegalArgumentException("ERROR | WE NOT EBALE TO  FIND  DAAT ON SERVICE LYEAR");
			}

			log.info("MESSAGE: WRITE LOGIN CREDENCIAL");
			return new ResponseEntity<>(candidateResponse, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR: DATA BASE LYEAR ERRROR ");
			return new ResponseEntity<>("ERROR | UNABLE TO LOGIN CANDIDATE DUE TO:" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login-candidate/with-email-otp")
	public ResponseEntity<Object> loginCandidate(@RequestParam("email") String email, @RequestParam("otp") int otp) {
		try {
			CandidateOtp otpResponse = this.candidateOtpData.findByEmail(email);
			if (otpResponse == null) {
				return new ResponseEntity<>("message : email is not valid", HttpStatus.BAD_REQUEST);
			}
			long noOfSeconds = otpResponse.getModifyAt().until(LocalDateTime.now(), ChronoUnit.SECONDS);
			long min = noOfSeconds / 60;

			if (otpResponse.getOtp() != otp) {
				log.warn("WARN! | OTP IS NOT VALID.");
				return new ResponseEntity<>("WARN : OTP IS NOT VALID", HttpStatus.BAD_GATEWAY);
			} else if (min > 2) {
				log.warn("WARN | OTP EXPAIRD.!");
				return new ResponseEntity<>("WARN | OTP EXPAIRED", HttpStatus.BAD_GATEWAY);
			}
			log.info("MESSAGE: REQUEST ACCEPTED AND DONE FOR CHANGE PASSWORD");
			return new ResponseEntity<>("MESSAGE | LOGIN SUCCESSFULLY !", HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR :INTERNAL SERVER ERROR");
			return new ResponseEntity<>("ERROR | UNABLE TO CHANGE YOU PASSWORD DUE TO :" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/reset-pass/candidate-req")
	public ResponseEntity<Object> resetCandidatePasswordReq(@RequestParam("Email") String email) {

		try {
			CandidateOtp candidateResponse = this.candidateService.getRequestToChangePass(email);
			if (candidateResponse == null) {
				log.error("ERROR: IN SERVICE LYEAR ERROR");
				throw new IllegalArgumentException("ERROR | WE NOT EBALE TO  FIND  DATA ON SERVICE LYEAR");
			}

			log.info("MESSAGE: REQUEST ACCEPTED FOR CHANGE PASSWORD");
			return new ResponseEntity<>(candidateResponse, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR: INTERNAL SERVER ERROR");
			return new ResponseEntity<>("ERROR | UNABLE TO ACCEPT TO CHANGE PASSWORD" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/reset-pass/candidate-res")
	public ResponseEntity<Object> resetCandidatePasswordRes(@RequestBody CandidatePassRequest candidateData) {

		CandidateOtp otpResponse = this.candidateOtpData.findByEmail(candidateData.getEmail());
		long noOfSeconds = otpResponse.getModifyAt().until(LocalDateTime.now(), ChronoUnit.SECONDS);
		long min = noOfSeconds / 60;

		if (otpResponse.getOtp() != candidateData.getOtp()) {
			log.warn("WARN! | OTP IS NOT VALID.");
			return new ResponseEntity<>("WARN : OTP IS NOT VALID", HttpStatus.BAD_GATEWAY);
		} else if (min > 2) {
			log.warn("WARN | OTP EXPAIRD.!");
			return new ResponseEntity<>("WARN | OTP EXPAIRED", HttpStatus.BAD_GATEWAY);
		}

		try {
			CandidatePassResponse candidateResponse = this.candidateService.resForChangePass(candidateData);
			if (candidateResponse == null) {
				log.error("ERROR: IN SERVICE LYEAR ERROR");
				throw new IllegalArgumentException("ERROR | WE NOT EBALE TO  FIND  DATA ON SERVICE LYEAR");
			}

			log.info("MESSAGE: REQUEST ACCEPTED AND DONE FOR CHANGE PASSWORD");
			return new ResponseEntity<>("MESSAGE | SUCCESSFULLY CHANGED PASSWORD", HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR: INTERNAL SERVER ERROR");
			return new ResponseEntity<>("ERROR | UNABLE TO CHANGE YOU PASSWORD DUE TO :" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/ss")
	public String ss() {
		return "ss";
	}

}
