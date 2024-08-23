package com.byter.sftj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byter.sftj.model.User;
import com.byter.sftj.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	public User save(User user) {
		return repo.save(user);
	}
	
	public Optional<User> findById(String username) {
		return repo.findById(username);
	}
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public void delete(User user) {
		repo.delete(user);
	}
}
