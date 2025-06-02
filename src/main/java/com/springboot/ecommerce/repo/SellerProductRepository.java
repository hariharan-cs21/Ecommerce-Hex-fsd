package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.SellerProduct;

public interface SellerProductRepository extends JpaRepository<SellerProduct, Integer> {
	List<SellerProduct> findByProductProductId(int productId);
    List<SellerProduct> findBySellerId(int sellerId);


	boolean existsBySellerIdAndProductProductId(int id, int productId);

}
