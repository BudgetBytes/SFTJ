package com.byter.sftj.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.byter.sftj.utils.Constants;
import com.byter.sftj.utils.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtFilter extends OncePerRequestFilter implements  Constants {
	private boolean isAuthenticated(HttpSession session) {
		String jwt = (String) session.getAttribute(SESSION_JWT);
		try {
			Jwt.validate(jwt);
			return true;
		} catch (Exception exc) {
			return false;
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		
		if (isAuthenticated(session) && (uri.contains("login") || uri.contains("register"))) {
			response.sendRedirect("/auth/home");
		} else if (!isAuthenticated(session) && uri.contains("auth")) {
			session.invalidate();
			filterChain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
