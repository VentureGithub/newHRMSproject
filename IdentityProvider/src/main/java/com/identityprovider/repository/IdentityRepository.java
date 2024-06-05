package com.identityprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.identityprovider.entity.Identity;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

	Identity findByEmail(String email);

	Identity findByEmpNumber(Long empNumber);

}
