package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.repo.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category getCategory(int id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found"));
	}

	public Category updateCategory(int categoryId, Category categoryDetails) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

		existingCategory.setCategoryName(categoryDetails.getCategoryName());

		return categoryRepository.save(existingCategory);
	}

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

}
