package com.springboot.ecommerce.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repo.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signUp(User user) {
		String plainPassword = user.getPassword(); 
		String encodedPassword =  passwordEncoder.encode(plainPassword);
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
	
	
}