package com.jwtexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtexample.jwt.JwtAuthenticationEntryPoint;
import com.jwtexample.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf(csrf -> csrf.disable())
					.cors(cors -> cors.disable())
					.authorizeRequests(
							auth -> auth.requestMatchers("/home/**")
							.authenticated()
							.requestMatchers("/auth/login").permitAll()
							.anyRequest().authenticated())
							.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
							.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		httpSecurity.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}
	
	

}
