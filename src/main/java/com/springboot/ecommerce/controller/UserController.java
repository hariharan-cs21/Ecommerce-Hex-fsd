package com.springboot.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public User signUp(@RequestBody User user ) {
		return userService.signUp(user);
	}
	
}
