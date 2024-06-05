package com.identityprovider.service;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.identityprovider.entity.Identity;
import com.identityprovider.repository.IdentityRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class adminAccessService {

	private IdentityRepository identityRepo;

	public adminAccessService(IdentityRepository identityRepo) {
		super();
		this.identityRepo = identityRepo;
	}

	@Transactional(rollbackOn = RuntimeException.class)
	public Identity freeshAccontOfUser(Long empNumber) {
		try {
			Identity identityEntity = this.identityRepo.findByEmpNumber(empNumber);
			if (identityEntity == null) {
				throw new NullPointerException("");
			}

			identityEntity.setStatus("OFF");
			Identity identityResponse = this.identityRepo.save(identityEntity);
			if (identityResponse == null) {
				throw new NullPointerException("");
			}
			return identityEntity;
		} catch (Exception e) {
			log.error("ERROR | user not register Successfully in database !");
			throw new DataRetrievalFailureException("ERROR : Failed to insert data" + e.getMessage());
		}

	}

}
