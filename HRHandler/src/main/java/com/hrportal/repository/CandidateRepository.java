package com.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrportal.entity.CandidateCredential;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateCredential, Long> {

	CandidateCredential findByEmail(String email);

}
