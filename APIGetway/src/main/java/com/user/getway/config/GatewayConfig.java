//package com.user.getway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class GatewayConfig {
//
//	private AuthenticationFilter filter;
//
//	private CorsFilter corsFilter;
//
//	public GatewayConfig(AuthenticationFilter filter, CorsFilter corsFilter) {
//		super();
//		this.filter = filter;
//		this.corsFilter = corsFilter;
//	}
//
//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//
////		Map<String, Object> corsConfigMap = new HashMap<>();
////		corsConfigMap.put("*", new CorsConfiguration().applyPermitDefaultValues());
//
//		return builder.routes().route("IDENTITY-PROVIDER",
//				r -> r.path("/identity-handler/**").filters(f -> f.filter(corsFilter)).uri("http://localhost:7000/"))
//				.route("hr-handler", r -> r.path("/hr-handler/**")
//						.filters(f -> f.filter(new RoleSettingFilter("HR", new RestTemplate()))).uri("lb://hr-handler"))
//				.route("admin-handler",
//						r -> r.path("/admin-handler/**")
//								.filters(f -> f.filter(new RoleSettingFilter("ADMIN", new RestTemplate())))
//								// .filters(f -> f.filter(filter)) // ................
//								.uri("lb://admin-handler"))
//				.route("user-handlerr",
//						r -> r.path("/user/**").filters(f -> f.filter(filter)).uri("lb://user-handlerr"))
//				.build();
//	}
//
////	@Bean
////	public CorsWebFilter corsWebFilter(GlobalCorsProperties globalCorsProperties) {
////		CorsConfiguration corsConfig = new CorsConfiguration();
////		corsConfig.setAllowedOrigins(globalCorsProperties.getCorsConfigurations().get("[/**]").getAllowedOrigins());
////		corsConfig.setAllowedMethods(globalCorsProperties.getCorsConfigurations().get("[/**]").getAllowedMethods());
////		corsConfig.setAllowedHeaders(globalCorsProperties.getCorsConfigurations().get("[/**]").getAllowedHeaders());
////
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", corsConfig);
////
////		return new CorsWebFilter(source);
////	}
//
////	@Bean
////	public CorsConfigurationSource corsConfigurationSourcee() {
////		CorsConfiguration corsConfig = new CorsConfiguration();
////		corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); // Replace with your frontend origin
////		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////		corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "X-Requested-With")); // Adjust as
////																											// needed
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", corsConfig);
////		return source;
////	}
//
////	@Bean
////	public CorsConfigurationSource corsConfigurationSourcee() {
////		CorsConfiguration corsConfig = new CorsConfiguration();
////		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:5173", "*"));
////		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////		corsConfig.setAllowedHeaders(Arrays.asList("*")); // Adjust allowed headers as needed
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", corsConfig);
////		return source;
////	}
//
////	@Bean
////	CorsConfigurationSource corsConfigurationSource() {
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
////		return source;
////	}
//
//}
