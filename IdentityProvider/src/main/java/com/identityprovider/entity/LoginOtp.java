package com.identityprovider.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "login_otp")
public class LoginOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "otp")
	private int otp;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "modify_at")
	private LocalDateTime modifyAt;

}
