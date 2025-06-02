package com.springboot.ecommerce.service;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repo.ExecutiveRepository;

@Service
public class ExecutiveService {
	private ExecutiveRepository executiveRepository;
	private UserService userService;


	
	public ExecutiveService(ExecutiveRepository executiveRepository,UserService userService) {
		this.executiveRepository = executiveRepository;
		this.userService = userService;

	}

	public Executive getExecutiveById(int id) {
	    return executiveRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Executive not found"));
	}
	public Executive getExecutiveByUsername(String username) {
		return executiveRepository.getExecutiveByUsername(username);
	}

	public Executive insertExecutive(Executive executive) {
			User user = executive.getUser();

			user.setRole("EXECUTIVE");
			user = userService.signUp(user);
			executive.setUser(user);
			return executiveRepository.save(executive);
		}
}
