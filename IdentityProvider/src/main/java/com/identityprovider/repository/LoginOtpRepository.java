package com.identityprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.identityprovider.entity.LoginOtp;

@Repository
public interface LoginOtpRepository extends JpaRepository<LoginOtp, Long> {

	LoginOtp findByEmail(String email);

	LoginOtp findByOtp(int otp);

}
