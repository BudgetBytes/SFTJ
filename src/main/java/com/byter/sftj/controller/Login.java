package com.byter.sftj.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.byter.sftj.model.User;
import com.byter.sftj.service.UserService;
import com.byter.sftj.utils.BCrypt;
import com.byter.sftj.utils.CheckAuth;
import com.byter.sftj.utils.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class Login implements Constants{
	@Autowired
	private UserService userService;
	
	@GetMapping("login")
	public ModelAndView login(HttpSession session) {
		if (CheckAuth.isAuthenticated(session)) {
			return new ModelAndView("redirect:/home");
		}
		
		return new ModelAndView("login");
	}
	
	@PostMapping("login")
	public ModelAndView login(HttpSession session, @RequestParam(required = true) String username, @RequestParam(required = true) String password ) {
		Optional<User> useropt = this.userService.findById(username);
		if (useropt.isEmpty()) {
			ModelAndView view = new ModelAndView("login");
			view.addObject(LOGIN_ERR, "Username does not exist");
			return view;
		}
		
		User user = useropt.get();
		if (!BCrypt.matches(password, user.getPassword())) {
			ModelAndView view = new ModelAndView("login");
			view.addObject(LOGIN_ERR, "Invalid credentials");
			return view;
		}
		
		session.setAttribute(SESSION_USER, user.getUsername());
		return new ModelAndView("redirect:/home");
	}
}
