package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.ProductRequest;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.service.ProductRequestService;
import com.springboot.ecommerce.service.ProductService;
import com.springboot.ecommerce.service.SellerService;

@RestController
public class SellerController {
	@Autowired
	private SellerService sellerService;
	
	
	@GetMapping("/api/seller/get-one")
	public Seller getSellerById(Principal principal) {
		String username = principal.getName();
		return sellerService.getSellerByUsername(username);
	}
	
	@PostMapping("/api/seller/register")
	public Seller insertSeller(@RequestBody Seller seller) {
		return sellerService.insertSeller(seller);
	}
	
	



}
