package com.jwtexample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jwtexample.dto.JwtRequest;
import com.jwtexample.dto.JwtResponse;
import com.jwtexample.jwt.JwtHelper;

@RestController
@RequestMapping("/auth")
public class JwtTokenGenerateController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper helper;

	public static final Logger log = LoggerFactory.getLogger(JwtTokenGenerateController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		this.doAuthenticate(request.getUserName(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
		String token = this.helper.generatetoken(userDetails);

		JwtResponse response = JwtResponse.builder().token(token).userName(userDetails.getUsername()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException ex) {
			log.error("Invalid userId or password");
			throw new BadCredentialsException("Invalid userId or password");
		}
	}
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid";
	}

}
