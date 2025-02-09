package com.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrportal.entity.CandidateDetails;

@Repository
public interface CandidateDetailsRepository extends JpaRepository<CandidateDetails, Long> {

}
