package com.springboot.ecommerce.service;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repo.SellerRepository;

@Service
public class SellerService {
	private SellerRepository sellerRepository;
	private UserService userService;


	
	public SellerService(SellerRepository sellerRepository,UserService userService) {
		this.sellerRepository = sellerRepository;
		this.userService = userService;

	}

	public Seller getSellerById(int id) {
	    return sellerRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Seller not found"));
	}
	public Seller getSellerByUsername(String username) {
		return sellerRepository.getSellerByUsername(username);
	}

	public Seller insertSeller(Seller seller) {
			User user = seller.getUser();

			user.setRole("SELLER");
			user = userService.signUp(user);
			seller.setUser(user);
			return sellerRepository.save(seller);
		}

}


