package com.springboot.ecommerce.service;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repo.CustomerRepository;

@Service
public class CustomerService {
	private CustomerRepository customerRepository;
	private UserService userService;
	
	public CustomerService(CustomerRepository customerRepository,UserService userService) {
		this.customerRepository = customerRepository;
		this.userService=userService;
	}
	public Customer getCustomerById(int id) {
	    return customerRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Customer not found"));
	}
	public Customer getCustomerByUsername(String username) {
		return customerRepository.getCustomerByUsername(username);
	}	
	public Customer insertCustomer(Customer customer) {
		User user = customer.getUser();

		user.setRole("CUSTOMER");
		user = userService.signUp(user);
		customer.setUser(user);
		return customerRepository.save(customer);
	}

}
