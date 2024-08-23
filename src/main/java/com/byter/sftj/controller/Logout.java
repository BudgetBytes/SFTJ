package com.byter.sftj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.byter.sftj.utils.CheckAuth;
import com.byter.sftj.utils.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class Logout implements Constants{
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		if (CheckAuth.isAuthenticated(session)) {
			session.removeAttribute(SESSION_USER);
		}
		
		return new ModelAndView("redirect:/");
	}
}
