package com.byter.sftj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	AuthSuccessHandler authSuccessHandler;
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.requiresChannel(channel -> channel.anyRequest().requiresSecure())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**").authenticated()
				.anyRequest().permitAll())
		.addFilterBefore(new JwtFilter(), BasicAuthenticationFilter.class)
		.formLogin(login -> login.loginPage("/login").permitAll().successHandler(authSuccessHandler))
		.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).logoutSuccessUrl("/login"));
		
		return http.build();
	}
}
