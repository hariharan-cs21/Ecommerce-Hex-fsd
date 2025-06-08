package com.springboot.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.WarehouseDispatch;

public interface WarehouseDispatchRepository extends JpaRepository<WarehouseDispatch, Integer> {
}