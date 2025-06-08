package com.springboot.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.WarehouseExecutive;

public interface WarehouseExecutiveRepository extends JpaRepository<WarehouseExecutive, Integer> {
	WarehouseExecutive getByUserUsername(String username);
}
