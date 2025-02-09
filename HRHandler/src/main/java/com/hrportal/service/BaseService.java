package com.hrportal.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@Service
@Slf4j
public class BaseService<S, K> {

	private ModelMapper modelMapper;

	public BaseService(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	@SuppressWarnings("unchecked")
	public K addDetails(JpaRepository<S, Long> repo, S entity, K dtoToReturn) {
		try {
			S responseEntityData = repo.save(entity);
			if (responseEntityData == null) {
				throw new IllegalArgumentException("error | unable to store data");
			}
			return (K) this.modelMapper.map(responseEntityData, dtoToReturn.getClass());

		} catch (Exception e) {
			log.error("error | internal server Error");
			throw new DataRetrievalFailureException("error : Failed to insert data due to: " + e.getMessage());
		}
	}

}
