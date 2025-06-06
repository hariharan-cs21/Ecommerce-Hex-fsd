package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomerId(int customerId);
}