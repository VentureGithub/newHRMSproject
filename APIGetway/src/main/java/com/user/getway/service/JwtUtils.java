package com.user.getway.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
@Component
public class JwtUtils {
	@Value("${jwt.secret}")
	private String secret;

//	public Authentication validateToken(final String token) {
//		return (Authentication) Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
//	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Key key;

	@PostConstruct
	public void initKey() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public boolean isExpired(String token) {
		try {
			return getClaims(token).getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}
