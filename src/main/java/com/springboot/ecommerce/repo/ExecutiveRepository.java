package com.springboot.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecommerce.model.Executive;

public interface ExecutiveRepository extends JpaRepository<Executive, Integer> {
	@Query("select e from Executive e where e.user.username=?1")
	Executive getExecutiveByUsername(String username);
}