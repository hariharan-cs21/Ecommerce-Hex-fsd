package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Address;
import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.repo.AddressRepository;

@Service
public class AddressService {
	private AddressRepository addressRepository;

	@Autowired
	CustomerService customerService;

	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public List<Address> getAddressesByCustomer(Customer customer) {
		return addressRepository.findAddressByCustomer(customer);

	}

	public Address addAddress(Address address) {
		return addressRepository.save(address);
	}

	public void deleteAddress(int addressId, String username) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address not found"));

		Customer customer = customerService.getCustomerByUsername(username);
		if (address.getCustomer().getId() != customer.getId()) {

			throw new RuntimeException("Not authorised to delete");
		}
		addressRepository.deleteById(addressId);
	}
}
