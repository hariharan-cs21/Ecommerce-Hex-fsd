package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategoryId(int categoryId);

}
