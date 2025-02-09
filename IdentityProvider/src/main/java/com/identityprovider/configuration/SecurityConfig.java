package com.identityprovider.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.identityprovider.helper.JwtAuthenticationEntryPoint;
import com.identityprovider.utiil.AppConstant;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private JwtAuthenticationEntryPoint point;

	private JwtAuthenticationFilter filter;

	public SecurityConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter) {
		super();
		this.point = point;
		this.filter = filter;
	}

	@Bean
	public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/").authenticated()
						/*
						 * .requestMatchers("/auth/**") .permitAll().requestMatchers("/**",
						 * "/api/getdata").hasRole("USER")
						 */
						.requestMatchers("/auth/**", "/**").permitAll().requestMatchers(AppConstant.allowedUrl)
						.permitAll().anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

}
