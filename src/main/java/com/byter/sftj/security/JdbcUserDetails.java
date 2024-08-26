package com.byter.sftj.security;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.byter.sftj.model.User;
import com.byter.sftj.repository.UserRepo;

@Configuration
public class JdbcUserDetails implements UserDetailsService {
	private UserRepo repo;
	
	public JdbcUserDetails(UserRepo userRepo) {
		this.repo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> useropt = repo.findById(username);
		if (useropt.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
				
		return org.springframework.security.core.userdetails.User.withUsername(username)
				.password(useropt.get().getPassword())
				.build();
	}
}
