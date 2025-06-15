package com.springboot.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecommerce.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
	@Query("select s from Seller s where s.user.username=?1")
	Seller getSellerByUsername(String username);

}
