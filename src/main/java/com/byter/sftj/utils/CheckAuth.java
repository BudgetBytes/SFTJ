package com.byter.sftj.utils;

import jakarta.servlet.http.HttpSession;

public class CheckAuth implements Constants{
	public static boolean isAuthenticated(HttpSession session) {
		String jwt = (String)session.getAttribute(SESSION_JWT);
		try {
			Jwt.validate(jwt);
			return true;
		} catch (Exception exc) {
			return false;
		}
	}
}
