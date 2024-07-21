package com.jwtexample.jwt;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		log.info("Header : {}", requestHeader);
		String userName = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			try {
				userName = this.jwtHelper.getUserNamefromToken(token);
			} catch (IllegalArgumentException e) {
				log.error("Illegal Argument while fetching the username");
				log.error(e.getMessage());
			} catch (ExpiredJwtException e) {
				log.error("Given Token is Expired");
				log.error(e.getMessage());
			} catch (MalformedJwtException e) {
				log.error("Some Change has been made in token. Invalid Token !!");
				log.error(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			log.error("Invalid Header value !!");
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			Boolean valiDateToken = this.jwtHelper.validateToken(token, userDetails);

			if (valiDateToken) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				log.error("Validation Fails !!");
			}

		}
		
		filterChain.doFilter(request, response);

	}
}
