package com.springboot.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
