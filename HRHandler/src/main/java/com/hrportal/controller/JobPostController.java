//package com.hrportal.controller;
//
//import java.time.LocalDateTime;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hrportal.entity.JobPost;
//
//import com.hrportal.model.request.JobPostRequest;
//import com.hrportal.repository.JobPostRepository;
//import com.hrportal.service.BaseService;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import lombok.extern.slf4j.Slf4j;
//
//@ComponentScan
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/job")
//@Slf4j
//public class JobPostController {
//
//	private BaseService<JobPost, > service;
//
//	private JobPostRepository jobPostRepo;
//
//	private DestinationSourceMapper mapper;
//
//	public JobPostController(BaseService<JobPost, JobPost> service, JobPostRepository jobPostRepo,
//			DestinationSourceMapper mapper) {
//		super();
//		this.service = service;
//		this.jobPostRepo = jobPostRepo;
//		this.mapper = mapper;
//	}
//
//	public ResponseEntity<Object> postJobForCanpany(@RequestBody JobPostRequest jobPostData) {
//		try {
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//}
