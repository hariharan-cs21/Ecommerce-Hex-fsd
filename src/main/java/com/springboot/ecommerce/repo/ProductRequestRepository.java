package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.ProductRequest;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
	List<ProductRequest> findBySellerUserUsername(String username);
	List<ProductRequest> findByApprovedFalse();

}
