package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Address;
import com.springboot.ecommerce.model.Customer;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAddressByCustomer(Customer customer);
}