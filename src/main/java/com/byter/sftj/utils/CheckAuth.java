package com.byter.sftj.utils;

import jakarta.servlet.http.HttpSession;

public class CheckAuth implements Constants{
	public static boolean isAuthenticated(HttpSession session) {
		return session.getAttribute(SESSION_USER) != null ? true : false;
	}
}
