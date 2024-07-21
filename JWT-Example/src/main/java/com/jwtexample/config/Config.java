package com.jwtexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class Config {

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails user1 = User.builder().username("Amrit").password(encoder().encode("abc")).roles("ADMIN").build();
		UserDetails user2 = User.builder().username("user2").password(encoder().encode("abc")).roles("ADMIN").build();

		return new InMemoryUserDetailsManager(user1, user2);

	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration  builder) throws Exception {
		return builder.getAuthenticationManager();
	}


}
