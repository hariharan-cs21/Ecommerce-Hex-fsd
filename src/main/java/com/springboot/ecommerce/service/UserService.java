package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.repo.CustomerRepository;
import com.springboot.ecommerce.repo.ExecutiveRepository;
import com.springboot.ecommerce.repo.SellerRepository;
import com.springboot.ecommerce.repo.UserRepository;
import com.springboot.ecommerce.repo.WarehouseExecutiveRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;
	private SellerRepository sellerRepository;
	private ExecutiveRepository executiveRepository;
	private WarehouseExecutiveRepository warehouseExecutiveRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			CustomerRepository customerRepository, SellerRepository sellerRepository,
			ExecutiveRepository executiveRepository, WarehouseExecutiveRepository warehouseExecutiveRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.sellerRepository = sellerRepository;
		this.executiveRepository = executiveRepository;
		this.warehouseExecutiveRepository = warehouseExecutiveRepository;

	}

	public User signUp(User user) {
		String plainPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(plainPassword);
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	public Object getUserInfo(String username) {
		User user = userRepository.getByUsername(username);
		switch (user.getRole().toUpperCase()) {
			case "CUSTOMER":
				return customerRepository.getCustomerByUsername(username);
			case "SELLER":
				return sellerRepository.getSellerByUsername(username);

			case "EXECUTIVE":
				return executiveRepository.getExecutiveByUsername(username);
			case "WAREHOUSE":
				return warehouseExecutiveRepository.getByUserUsername(username);
			default:
				return null;
		}
	}

	public List<Seller> getAllSellers(String name) {
		executiveRepository.getExecutiveByUsername(name);
		return sellerRepository.findAll();
	}

}