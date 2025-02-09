package com.hrportal.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrportal.entity.CandidateCredential;
import com.hrportal.entity.CandidateOtp;
import com.hrportal.model.request.CandidateLoginRequest;
import com.hrportal.model.request.CandidatePassRequest;
import com.hrportal.model.response.CandidatePassResponse;
import com.hrportal.repository.CandidateOtpRepository;
import com.hrportal.repository.CandidateRepository;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@Service
@Slf4j
public class CandidateService {

	@Autowired
	private ModelMapper mapp;

	private JavaMailSender mailsender;

	private CandidateRepository candidateRepo;

	private CandidateOtpRepository candidateOtpRepo;

	public CandidateService(CandidateRepository candidateRepo, JavaMailSender mailsender,
			CandidateOtpRepository candidateOtpRepo) {
		super();
		this.candidateRepo = candidateRepo;
		this.mailsender = mailsender;
		this.candidateOtpRepo = candidateOtpRepo;

	}

	@Transactional(rollbackFor = RuntimeException.class)
	public CandidateCredential addCondidateResume(String email) {

		if (email instanceof String) {
			CandidatePassResponse dataa = this.mapp.map(new Email(email), CandidatePassResponse.class);
			System.out.print("bhai ye to object hai " + dataa);
		}

		CandidateCredential candidateData = new CandidateCredential();
		CandidateOtp otpEntity = new CandidateOtp();
		int otp = generateOtp();
		try {
			int numberOfCandidate = (int) this.candidateRepo.count();
			String condidateId = generateCandidateId(email, numberOfCandidate);

			candidateData.setCandidateId(condidateId);
			candidateData.setEmail(email);
			candidateData.setPassword("welcome@123");
			candidateData.setCretedAt(LocalDateTime.now());
			candidateData.setModifyAt(LocalDateTime.now());

			CandidateCredential candidateResponse = this.candidateRepo.save(candidateData);
			if (candidateResponse == null) {
				log.error("error | fail to insert candidate Resume");
				throw new IllegalArgumentException("error : unable to store data of candidate resume");
			}
			log.info("message | candidate data load in database");

			otpEntity.setCandidateId(condidateId);
			otpEntity.setEmail(email);
			otpEntity.setOtp(otp);
			otpEntity.setCreateAt(LocalDateTime.now());
			otpEntity.setModifyAt(LocalDateTime.now());
			CandidateOtp responseOtpData = this.candidateOtpRepo.save(otpEntity);
			if (responseOtpData == null) {
				log.error("error | otp detail not set");
				throw new IllegalArgumentException("error : unable to set otp details");
			}
			log.info("message | set otp details");
			return candidateData;
		} catch (Exception e) {
			log.error("error | user not register Successfully in database !");
			throw new DataRetrievalFailureException("error : unable to insert data" + e.getMessage());
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public CandidateOtp getRequestToChangePass(String email) {
		int otp = generateOtp();
		try {
			CandidateCredential candidateData = this.candidateRepo.findByEmail(email);
			if (candidateData == null) {
				log.error("error | email is not valid");
				throw new IllegalArgumentException("error : email is not valid");
			}
			log.info("message | git candidate data by email");

			CandidateOtp candidateOtp = this.candidateOtpRepo.findByEmail(email);
			if (candidateOtp == null) {
				log.error("error | otp not set");
				throw new IllegalArgumentException("error : unable to find Otp Data");
			}
			candidateOtp.setOtp(otp);
			candidateOtp.setModifyAt(LocalDateTime.now());
			CandidateOtp candidateOtpData = this.candidateOtpRepo.save(candidateOtp);
			if (candidateOtpData == null) {
				log.error("error | otp not store");
				throw new IllegalArgumentException("error : otp not store in database");
			}
			log.info("message | otp update successfully");
			// emailOtpGenerated(email);
			log.info("message | send otp on email");
			return candidateOtp;

		} catch (Exception e) {
			log.error("error | internal server Error !");
			throw new DataRetrievalFailureException("error : Failed to insert data" + e.getMessage());
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public CandidatePassResponse resForChangePass(CandidatePassRequest requestData) {

		try {
			CandidateCredential candidateData = this.candidateRepo.findByEmail(requestData.getEmail());
			if (candidateData == null) {
				log.error("error | email not valid");
				throw new IllegalArgumentException("error : email is not exist in database");
			}
			log.info("message | find candidate data by email");

			candidateData.setPassword(requestData.getPassword());
			CandidateCredential candidateResponseData = this.candidateRepo.save(candidateData);
			if (candidateResponseData == null) {
				log.error("error | password not changed");
				throw new IllegalArgumentException("error : unable to change the candidate password");
			}
			log.info("message | Successfully Changed Password");

			// CandidatePassResponse responseData =
			// mapp.candiCredToCandiPassRes(candidateResponseData);
			// responseData.setStatus("Successfully Change Password");

			CandidatePassResponse responseData = this.mapp.map(candidateResponseData, CandidatePassResponse.class);

			return responseData;

		} catch (Exception e) {
			log.error("error | internal server Error !");
			throw new DataRetrievalFailureException("error : Failed to insert data" + e.getMessage());
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public CandidateOtp loginWithEmailAndPass(CandidateLoginRequest loginData) {

		int otp = generateOtp();
		try {
			CandidateCredential candidateData = this.candidateRepo.findByEmail(loginData.getEmail());
			if (!candidateData.getPassword().equals(loginData.getPassword())) {
				log.error("error | wrong password ");
				throw new IllegalArgumentException("Error: wrong password");
			}
			log.info("message | right credencial");

			CandidateOtp candidateOtp = this.candidateOtpRepo.findByEmail(loginData.getEmail());
			if (candidateOtp == null) {
				log.error("error | otp not set");
				throw new IllegalArgumentException("error : unable to find Otp Data");
			}
			candidateOtp.setOtp(otp);
			candidateOtp.setModifyAt(LocalDateTime.now());
			CandidateOtp candidateOtpData = this.candidateOtpRepo.save(candidateOtp);
			if (candidateOtpData == null) {
				log.error("error | otp not store");
				throw new IllegalArgumentException("error : unable to update otp in database");
			}
			log.info("message | otp store successfully");
			return candidateOtpData;
		} catch (Exception e) {
			log.error("error | internal server Error");
			throw new DataRetrievalFailureException("error : Failed to insert data due to: " + e.getMessage());
		}
	}

	// Other Method with are help API method for fill the data
	// .....................................

	public String generateCandidateId(String email, int number) {
		String[] sss = email.split("@");
		return number + "000" + sss[0].toUpperCase();
	}

	public int generateOtp() {
		return (int) (Math.random() * 9000) + 1000;
	}

	public String emailOtpGenerated(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("yaadavhemant20056@gmail.com");
		message.setTo(email);
		String otp = Integer.toString((int) (Math.random() * 9000) + 1000);
		message.setText("\r\n" + "Hi ,\r\n" + "\r\n" + "we are sharing a onetime password to access the "
				+ "Request more details. The password is valid " + "for 72 hours from the request."
				+ " Please do not share it with anyone ,\r\n" + "\r\n" + "Username" + ":" + email + "\r\n" + "OTP" + ":"
				+ otp);
		message.setSubject("OTP to access more details");
		mailsender.send(message);
		return otp;
	}
}

class Email {
	String email;

	Email(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
