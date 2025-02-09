package com.hrportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrportal.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

}
