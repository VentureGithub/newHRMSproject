package com.identityprovider.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "identity")
public class Identity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4512697385733714207L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "password")
	private String password;

	@Column(name = "emp_number")
	private Long empNumber;

	@Column(name = "emp_code")
	private String empCode;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority ss = new SimpleGrantedAuthority(this.role);
		return List.of(ss);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
