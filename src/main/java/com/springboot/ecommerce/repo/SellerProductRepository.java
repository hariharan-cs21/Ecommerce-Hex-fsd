package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecommerce.model.SellerProduct;

public interface SellerProductRepository extends JpaRepository<SellerProduct, Integer> {
	List<SellerProduct> findByProductProductId(int productId);

	List<SellerProduct> findBySellerId(int sellerId);

	boolean existsBySellerIdAndProductProductId(int id, int productId);

	@Query("select sp.stockQuantity from SellerProduct sp where sp.id=?1")
	int getStockBySellerProduct(int sellerProductId);

}
