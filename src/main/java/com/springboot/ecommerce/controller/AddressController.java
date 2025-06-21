package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Address;
import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.service.AddressService;
import com.springboot.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {
	private AddressService addressService;
	private CustomerService customerService;

	public AddressController(AddressService addressService, CustomerService customerService) {
		this.addressService = addressService;
		this.customerService = customerService;
	}

	@GetMapping("/list")
	public List<Address> getAddresses(Principal principal) {
		Customer customer = customerService.getCustomerByUsername(principal.getName());
		return addressService.getAddressesByCustomer(customer);
	}

	@PostMapping("/add")
	public Address addAddress(@RequestBody Address address, Principal principal) {
		Customer customer = customerService.getCustomerByUsername(principal.getName());
		address.setCustomer(customer);
		return addressService.addAddress(address);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteAddress(@PathVariable int id, Principal principal) {
		addressService.deleteAddress(id, principal.getName());
	}
	@PutMapping("/update/{id}")
    public Address updateAddress(@PathVariable int id, @RequestBody Address address, Principal principal) {
        Customer customer = customerService.getCustomerByUsername(principal.getName());
        address.setCustomer(customer); 
        return addressService.updateAddress(id, address);  
    }
}
