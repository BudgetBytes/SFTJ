package com.byter.sftj.controller;

import java.io.File;
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
import com.byter.sftj.utils.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class NotAuthenticated implements Constants{
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView root(HttpSession session) {
		return new ModelAndView("login");
	}
	
	@GetMapping("login") 
	public ModelAndView login(HttpSession session) {
		return new ModelAndView("login");
	}
	
	@GetMapping("register")
	public ModelAndView register(HttpSession session) {
		return new ModelAndView("register");
	}
	
	@PostMapping("register")
	public ModelAndView register(HttpSession session, @RequestParam(required = true) String username, @RequestParam(required = true) String password ) {
		ModelAndView view = new ModelAndView("register");
		Optional<User> useropt = userService.findById(username);
		
		if (useropt.isPresent()) {
			view.addObject(REGISTER_ERR, "User '" + username + "' already exists");
			return view;
		}
		
		userService.save(new User(username, BCrypt.encode(password)));
		File baseDir = new File(BASE_DIR + "/" + username);
		baseDir.mkdirs();
		
		return view;
	}
}
