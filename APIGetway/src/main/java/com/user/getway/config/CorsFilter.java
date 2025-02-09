package com.user.getway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorsFilter implements GatewayFilter {

//	@Bean
//	public CorsWebFilter corsWebFilter() {
//
//		final CorsConfiguration corsConfig = new CorsConfiguration();
//		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
//		corsConfig.setMaxAge(3600L);
//		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
//		corsConfig.addAllowedHeader("*");
//
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfig);
//
//		return new CorsWebFilter(source);
//	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		// corsWebFilter();
		// return null;
		return chain.filter(exchange);
	}

}
