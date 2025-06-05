package com.springboot.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByCustomerId(int customerId);
}