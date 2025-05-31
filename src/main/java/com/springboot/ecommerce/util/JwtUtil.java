package com.springboot.ecommerce.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String secretKey = "secretsecretsecretsecretsecretsecretsecretsecret!";
	private static final long expirationTimeInMills=43200000; 
	private Key getSigningKey(){
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	public String createToken(String subject){
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		
		 return Jwts.builder()
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMills))
	                .signWith(key)
	                .compact();
		}
	public boolean verifyToken(String token, String email) {
		String extractedEmail = Jwts.parserBuilder()
									.setSigningKey(getSigningKey())
									 .build()
									 .parseClaimsJws(token)
									 .getBody()
									 .getSubject();
		Date expirationDate =  Jwts.parserBuilder()
									.setSigningKey(getSigningKey())
									 .build()
									 .parseClaimsJws(token)
									 .getBody()
									 .getExpiration();
		
		return extractedEmail.equals(email) && new Date().before(expirationDate); 			
	}
	public String extractUsername(String token) {
		 
		return  Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				 .build()
				 .parseClaimsJws(token)
				 .getBody()
				 .getSubject(); 
	}
}
