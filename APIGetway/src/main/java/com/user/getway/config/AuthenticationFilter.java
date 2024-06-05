package com.user.getway.config;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.user.getway.service.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ComponentScan
@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RouterValidator validator;

	@Autowired
	private JwtUtils jwtUtils;

//	public AuthenticationFilter(RestTemplate restTemplate, RouterValidator validator, JwtUtils jwtUtils) {
//		super();
//		this.validator = validator;
//		this.restTemplate = restTemplate;
//		this.jwtUtils = jwtUtils;
//	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		if (validator.isSecured.test(request)) {
			if (authMissing(request)) {
				return onError(exchange, HttpStatus.UNAUTHORIZED);
			}

			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

			if (jwtUtils.isExpired(token)) {
				return onError(exchange, HttpStatus.UNAUTHORIZED);
			}

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

			byte[] decodedBytes = Base64.getDecoder().decode(authHeader);
			String decodedString = new String(decodedBytes);
			authHeader = decodedString;
			restTemplate.getForObject("http://localhost:7000/identity-handler/check-point/validate?token=" + authHeader,
					String.class);

			List<String> userRoles = extractUserRoles(authHeader);
			log.info("MASSEGE |  TOKEN  USERNAME IS :" + userRoles);

		}
		return chain.filter(exchange);
	}

	private static Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private static boolean authMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	// Replace with your implementation to extract roles from Authentication object
	private List<String> extractUserRoles(String token) {
		@SuppressWarnings("unchecked")
		List<String> data = restTemplate.getForObject(
				"http://localhost:7000/identity-handler/check-point/validate/role?token=" + token, List.class);
		return data;
	}

}
