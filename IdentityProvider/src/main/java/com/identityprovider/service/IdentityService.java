package com.identityprovider.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.identityprovider.entity.Identity;
import com.identityprovider.entity.LoginOtp;
import com.identityprovider.model.request.JwtRequest;
import com.identityprovider.model.request.LoginRequest;
import com.identityprovider.model.request.SignUpRequest;
import com.identityprovider.model.response.LoginSignUpResponse;
import com.identityprovider.model.response.SignUpResponse;
import com.identityprovider.repository.IdentityRepository;
import com.identityprovider.repository.LoginOtpRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ComponentScan
@Service
@Slf4j
public class IdentityService implements UserDetailsService {

	private IdentityRepository isentityRepo;

	private PasswordEncoder passwordEncoder;

	private LoginOtpRepository loginOtpRepo;

	public IdentityService(IdentityRepository isentityRepo, PasswordEncoder passwordEncoder,
			LoginOtpRepository loginOtpRepo) {
		super();
		this.isentityRepo = isentityRepo;
		this.passwordEncoder = passwordEncoder;
		this.loginOtpRepo = loginOtpRepo;
	}

	@Transactional(rollbackOn = RuntimeException.class)
	public SignUpResponse createCredential(SignUpRequest signUpData) {

		Identity identityEntity = new Identity();
		SignUpResponse responseData = new SignUpResponse();
		int otp = generateRandomOtp();
		long countEmp = this.isentityRepo.count();
		String empCode = geneerateEmpCode(signUpData.getName(), countEmp);
		LoginOtp loginEntity = new LoginOtp();
		try {

			identityEntity.setName(signUpData.getName());
			identityEntity.setEmail(signUpData.getEmail());
			identityEntity.setMobileNumber(signUpData.getMobileNumber());
			identityEntity.setPassword(this.passwordEncoder.encode(signUpData.getPassword()));
			identityEntity.setEmpNumber(countEmp + 1);
			identityEntity.setEmpCode(empCode);
			identityEntity.setRole(signUpData.getRole());
			identityEntity.setCreatedAt(LocalDateTime.now());
			identityEntity.setStatus("ON");

			Identity identityResponse = this.isentityRepo.save(identityEntity);
			if (identityResponse == null) {
				log.error("ERROR | emp not registerd");
				throw new IllegalArgumentException("ERROR : unable to store data");
			}
			log.info("MESSAGE | Emp registerd");

			loginEntity.setEmail(identityResponse.getEmail());
			loginEntity.setOtp(otp);
			loginEntity.setModifyAt(LocalDateTime.now());
			loginEntity.setCreatedAt(LocalDateTime.now());
			LoginOtp loginOtpResponse = this.loginOtpRepo.save(loginEntity);
			if (loginOtpResponse == null) {
				log.error("ERROR | otp not save in database");
				throw new IllegalArgumentException("ERROR : unable to store otpdata");
			}
			log.info("MESSAGE | otpdata store successfully ");

			responseData.setName(signUpData.getName());
			responseData.setEmail(signUpData.getEmail());
			responseData.setMobileNumber(signUpData.getMobileNumber());
			responseData.setPassword(signUpData.getPassword());
			responseData.setEmpCode(identityResponse.getEmpCode());
			responseData.setEmpNumber(identityResponse.getEmpNumber());
			responseData.setCreatedAt(identityResponse.getCreatedAt());
			responseData.setStatus("created");
			log.info("MESSAGE | successfully response");
			return responseData;

		} catch (Exception e) {
			log.error("ERROR | user not register Successfully in database !");
			throw new DataRetrievalFailureException("ERROR : Failed to insert data" + e.getMessage());
		}
	}

	@Transactional(rollbackOn = RuntimeException.class)
	public LoginSignUpResponse loginUser(LoginRequest loginData) {

		LoginSignUpResponse responseData = new LoginSignUpResponse();
		int otp = generateRandomOtp();
		try {

			LoginOtp getLoginOtpData = this.loginOtpRepo.findByEmail(loginData.getEmail());
			if (getLoginOtpData == null) {
				log.error("ERROR | wrong  credential");
				throw new IllegalArgumentException("ERROR | unable to find otpdata");
			}
			log.info("MASSEGE | get otpdata");

			Identity identityResponse = this.isentityRepo.findByEmail(loginData.getEmail());
			if (identityResponse == null) {
				log.error("ERROR | wrong  credential");
				throw new IllegalArgumentException(" ERROR | email not exist in database");
			}
			log.info("MESSAGE | fetch data by email");

			if (!this.passwordEncoder.matches(loginData.getPassword(), identityResponse.getPassword())) {
				throw new IllegalArgumentException("ERROR | password not match");
			}

			if (Objects.equals(identityResponse.getStatus(), "OFF")) {
				log.error("ERROR | your data deleted");
				throw new IllegalArgumentException("EORROR : you are not exists");
			}

			getLoginOtpData.setOtp(otp);
			getLoginOtpData.setModifyAt(LocalDateTime.now());
			LoginOtp loginOtpResponse = this.loginOtpRepo.save(getLoginOtpData);
			if (loginOtpResponse == null) {
				log.error("ERROR | otp not update ");
				throw new IllegalArgumentException("ERROR | otp not update with date");
			}
			log.info("MASSEGE | otp update");

			responseData.setName(identityResponse.getName());
			responseData.setOtp(otp);
			responseData.setStstus("right");

			log.info("MESSAGE | login successfully");
			return responseData;
		} catch (Exception e) {
			log.error("ERROR | user not register Successfully in database !");
			throw new DataRetrievalFailureException("ERROR : Failed to insert data" + e.getMessage());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.isentityRepo.findByEmail(username);
	}

	@SuppressWarnings("null")
	public boolean checkOtp(JwtRequest data) {
		boolean response = true;
		LoginOtp loginOtpResponse = this.loginOtpRepo.findByOtp(data.getOtp());
		if (loginOtpResponse == null && !Objects.equals(loginOtpResponse.getEmail(), data.getEmail())) {
			return false;
		}
		return response;
	}

	private static int generateRandomOtp() {
		Random random = new Random();
		return 1000 + random.nextInt(9000);
	}

	private static String geneerateEmpCode(String name, long empNo) {
		String str = empNo + "";
		return name.substring(0, 2).toUpperCase() + str + "NO";
	}

}
