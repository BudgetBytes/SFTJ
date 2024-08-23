package com.byter.sftj.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.byter.sftj.model.File;
import com.byter.sftj.model.User;
import com.byter.sftj.service.FileService;
import com.byter.sftj.service.UserService;
import com.byter.sftj.utils.CheckAuth;
import com.byter.sftj.utils.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class Home implements Constants {
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	
	@GetMapping("/")
	public ModelAndView root(HttpSession session) {
		if (!CheckAuth.isAuthenticated(session)) 
			return new ModelAndView("redirect:/login");
		
		return new ModelAndView("redirect:/home");
	}
	
	@GetMapping("home")
	public ModelAndView home(HttpSession session) {
		if (!CheckAuth.isAuthenticated(session)) 
			return new ModelAndView("redirect:/login");

		
		ModelAndView view = new ModelAndView("home");
		
		Optional<User> useropt = userService.findById((String)session.getAttribute(SESSION_USER));
		if (useropt.isEmpty()) {
			view.addObject(DAHSBOARD_ERR, "Something went wrong. Try logout and log back in");
			return view;
		}
		
		view.addObject(USER_FILES, useropt.get().getFiles());		
		return view;
	}
	
	@PostMapping("upload")
	public ModelAndView upload(HttpSession session, @RequestParam MultipartFile file) throws IOException {
		String path = BASE_DIR + "/" + session.getAttribute(SESSION_USER)  + "/" + file.getOriginalFilename();
		java.io.File target = new java.io.File(path);

		try (OutputStream os = new FileOutputStream(target)) {
		    os.write(file.getBytes());
		}
		
		fileService.save(new com.byter.sftj.model.File(file.getOriginalFilename(), path, new Date(), (String) session.getAttribute(SESSION_USER)));
		
		return new ModelAndView("redirect:/home");
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView delete(HttpSession session, @PathVariable Long id) {
		Optional<File> fileopt = fileService.findById(id);
		
		ModelAndView view = new ModelAndView("redirect:/home");
		
		if (fileopt.isEmpty()) {
			view.addObject("error", "File not found");
			return view;
		}
		
		if (!fileopt.get().getUsername().equals((String) session.getAttribute(SESSION_USER))){
			view.addObject("error", "This is not your file");
			return view;
		}
		
		fileService.delete(fileopt.get());
		java.io.File file = new java.io.File(fileopt.get().getPath());
		
	    if (!file.delete()) {
	    	view.addObject("error", "Failed to delete file");
	    	return view;
	    }

	    return view;
	}
	
	@GetMapping("download/{id}")
	public ResponseEntity<Resource> download(HttpSession session, @PathVariable Long id) {
	    Optional<File> fileopt = fileService.findById(id);
	    
	    if (fileopt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    
	    if (!fileopt.get().getUsername().equals((String) session.getAttribute(SESSION_USER))) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }

	    java.io.File file = new java.io.File(fileopt.get().getPath());
	    
	    if (!file.exists()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    Resource resource = null;
	    try {
	        resource = new UrlResource(file.toURI());
	    } catch (MalformedURLException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	            .body(resource);
	}	
}
