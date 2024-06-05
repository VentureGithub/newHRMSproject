package com.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrportal.entity.CandidateOtp;

@Repository
public interface CandidateOtpRepository extends JpaRepository<CandidateOtp, Long> {

	CandidateOtp findByEmail(String email);

}
