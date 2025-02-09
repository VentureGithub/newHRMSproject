package com.user.getway.config;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

//@Order(1)
public class RoleSettingFilter implements GatewayFilterFactory {

	@Value("${inputFilePath}")
	@Autowired
	private String role;

//	@Autowired
//	private JwtUtils jwtUtils;

	// @Value("${inputFilePath}")
	@Autowired
	private RestTemplate restTemplate;

//	public RoleSettingFilter(@Value("${myStringBean}") String role, RestTemplate restTemplate) {
//		super();
//		this.role = role;
//		this.restTemplate = restTemplate;
//	}

	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		if (authMissing(request)) {
			return onError(exchange, HttpStatus.UNAUTHORIZED);
		}
		// final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

		String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

		byte[] decodedBytes = Base64.getDecoder().decode(authHeader);
		String decodedString = new String(decodedBytes);
		authHeader = decodedString;

		this.restTemplate.getForObject(
				"http://localhost:7000/identity-handler/check-point/validate?token=" + authHeader, String.class);

		List<String> userRoles = extractUserRoles(authHeader);
		if (!userRoles.contains(role)) {
			return onError(exchange, HttpStatus.FORBIDDEN);
		}
		return chain.filter(exchange);
	}

	private List<String> extractUserRoles(String token) {
		@SuppressWarnings("unchecked")
		List<String> data = restTemplate.getForObject(
				"http://localhost:7000/identity-handler/check-point/validate/role?token=" + token, List.class);
		return data;
	}

	private static Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private static boolean authMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	@Override
	public GatewayFilter apply(Object config) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
////		ServerHttpRequest request = exchange.getRequest();
////		if (authMissing(request)) {
////			return onError(exchange, HttpStatus.UNAUTHORIZED);
////		}
//		// final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
//
//		String authHeader = request.getHeader("Authorization");
//
//		byte[] decodedBytes = Base64.getDecoder().decode(authHeader);
//		String decodedString = new String(decodedBytes);
//		authHeader = decodedString;
//
//		this.restTemplate.getForObject(
//				"http://localhost:7000/identity-handler/check-point/validate?token=" + authHeader, String.class);
//
//		List<String> userRoles = extractUserRoles(authHeader);
//		if (!userRoles.contains(role)) {
//			throw new NullPointerException("exeption");
//		}
//		// return chain.filter(exchange);
//	}

}
