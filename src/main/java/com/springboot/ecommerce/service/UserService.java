package com.springboot.ecommerce.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repo.CustomerRepository;
import com.springboot.ecommerce.repo.ExecutiveRepository;
import com.springboot.ecommerce.repo.SellerRepository;
import com.springboot.ecommerce.repo.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;
	private SellerRepository sellerRepository;
	private ExecutiveRepository executiveRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			CustomerRepository customerRepository, SellerRepository sellerRepository,
			ExecutiveRepository executiveRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.sellerRepository = sellerRepository;
		this.executiveRepository = executiveRepository;

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
			default:
				return null;
		}
	}

}