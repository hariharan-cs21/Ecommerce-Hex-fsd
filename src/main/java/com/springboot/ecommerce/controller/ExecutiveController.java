package com.springboot.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.service.ExecutiveService;

@RestController
public class ExecutiveController {

	@Autowired
	private ExecutiveService executiveService;

	@GetMapping("/api/executive/get-one")
	public Executive getSellerById(Principal principal) {
		String username = principal.getName();
		return executiveService.getExecutiveByUsername(username);
	}

	@PostMapping("/api/executive/register")
	public Executive insertSeller(@RequestBody Executive executive) {
		return executiveService.insertExecutive(executive);
	}

}