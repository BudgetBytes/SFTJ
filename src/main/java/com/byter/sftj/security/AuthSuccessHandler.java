package com.byter.sftj.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.byter.sftj.utils.Constants;
import com.byter.sftj.utils.Jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler, Constants {
	@Autowired
	HttpSession session;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String jwt = Jwt.generate(authentication.getName());
		session.setAttribute(SESSION_JWT, jwt);
		session.setAttribute(SESSION_USERNAME, authentication.getName());
		response.sendRedirect("/auth/home");
	}
}
