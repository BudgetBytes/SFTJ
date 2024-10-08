package com.byter.sftj.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt {
	public static String encode(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static boolean matches(String password, String hash) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, hash);
	}
}
