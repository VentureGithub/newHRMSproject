package com.user.getway.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.user.getway.service.JwtUtils;

@Component
public class AuthenticationFilterr extends AbstractGatewayFilterFactory<AuthenticationFilterr.Config> {

//	@Autowired
//	private RouterValidator validator;

	@Autowired
	private RouterValidator validator;

	private static final String VALUE = "value";

	// @Autowired
//    private RestTemplate template;
	@Autowired
	private JwtUtils jwtUtil;

	public AuthenticationFilterr() {
		super(Config.class);
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Collections.singletonList(VALUE);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			// ServerHttpRequest request = exchange.getRequest();
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
					// jwtUtil.validateToken(authHeader);
					System.out.print(config.getValue() + "jjjjjjj");
				} catch (Exception e) {
					System.out.println("invalid access...!");
					throw new RuntimeException("un authorized access to application");
				}
			}
			return chain.filter(exchange);
		});
	}

	public static class Config {

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}