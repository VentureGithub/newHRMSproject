package com.hrportal.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrportal.entity.CandidateDetails;
import com.hrportal.model.CandidateDetailsDTO;
import com.hrportal.model.response.CandidatePassResponse;
import com.hrportal.repository.CandidateDetailsRepository;
import com.hrportal.service.BaseService;
import com.hrportal.service.Conversion;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@RestController
@Slf4j
public class CaandidateDetailsController {

	private BaseService<CandidateDetails, CandidatePassResponse> candidateDetailservice;

	private CandidateDetailsRepository candidateRepo;

	private Conversion<?, CandidateDetailsDTO> convert;

	public CaandidateDetailsController(BaseService<CandidateDetails, CandidatePassResponse> candidateDetailservice,
			CandidateDetailsRepository candidateRepo, Conversion<?, CandidateDetailsDTO> convert) {
		super();
		this.candidateDetailservice = candidateDetailservice;
		this.candidateRepo = candidateRepo;
		this.convert = convert;
	}

	@PostMapping("/add/candidate-details")
	public ResponseEntity<Object> ssdd(@RequestBody CandidateDetailsDTO detailsData) {
		try {
			CandidatePassResponse data = this.candidateDetailservice.addDetails(candidateRepo,
					(CandidateDetails) this.convert.baseConversion(CandidateDetails.class, detailsData),
					new CandidatePassResponse());
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			log.error("ERROR | unable to  store candidateDetails");
			return new ResponseEntity<>("bad", HttpStatus.BAD_GATEWAY);
		}
	}

}
