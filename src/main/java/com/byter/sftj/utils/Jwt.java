package com.byter.sftj.utils;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Jwt implements Constants {
	private static String jwtSecret = getRandomSecret();
	
	private static String getRandomSecret() {
        String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 54) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	public static String generate(String username) {
		System.out.println("SECRET: " + jwtSecret);
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS256.getJcaName());
		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
				.setSubject(username)
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(JWT_EXPIRATION, UNIT)))
				.signWith(hmacKey)
				.compact();
		return jwtToken;
	}
	
	public static Jws<Claims> validate(String jwtToken) {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS256.getJcaName());
		
		Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(hmacKey)
				.build()
				.parseClaimsJws(jwtToken);
		
		return claims;
	} 
	
}
