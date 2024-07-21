package com.jwtexample.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	public String Secret = "wertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcv"
			+ "bnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfgwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmwertyuiopasdfghjklzxcvbnmhjklzxcvbnm";
	
	// get UserName from JWT Token
	public String getUserNamefromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);	
	}
	
	//get Expiration Date from JWT Token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);	
	}
	
	public <T> T getClaimFromToken(String token , Function<Claims , T> claimsResolver) {
		final Claims claims = getAllClaimsfromToken(token);
		return claimsResolver.apply(claims);
	}
	
	@SuppressWarnings("deprecation")
	private Claims getAllClaimsfromToken(String token) {
		return Jwts.parser().setSigningKey(Secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String Token) {
		final Date expiration = getExpirationDateFromToken(Token);
		return expiration.before(new Date());
		
	}
	
	public String generatetoken(UserDetails userDetails) {
		Map<String,Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims , userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String , Object> claims , String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512,Secret).compact();
	}
	
	
	public Boolean validateToken(String token , UserDetails userDetails) {
		final String userName = getUserNamefromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	

}
