package com.byter.sftj.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.byter.sftj.model.User;
import com.byter.sftj.service.UserService;
import com.byter.sftj.utils.CheckAuth;
import com.byter.sftj.utils.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class Logout implements Constants{
	@Autowired
	private UserService userService;
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		if (CheckAuth.isAuthenticated(session)) {
			session.removeAttribute(SESSION_USER);
		}
		
		return new ModelAndView("redirect:/");
	}
	
	@PostMapping("deleteAccount")
	public ModelAndView delete(HttpSession session) {
		if (!CheckAuth.isAuthenticated(session)) {
			return new ModelAndView("redirect:/");
		}
		
		ModelAndView view = new ModelAndView("home");

		Optional<User> useropt = userService.findById((String) session.getAttribute(SESSION_USER));		
		if (useropt.isEmpty()) {
			view.addObject(DAHSBOARD_ERR, "Unable to find user to delete");
			return view;
		}
		
		userService.delete(useropt.get());
		
		try {
			FileUtils.deleteDirectory(new File(BASE_DIR + "/" + useropt.get().getUsername()));
		} catch (IOException e) {
			e.printStackTrace();
			view.addObject(DAHSBOARD_ERR, "Unable to delete user folder");
			return view;
		}
		
		return new ModelAndView("redirect:/logout");
	}
}
