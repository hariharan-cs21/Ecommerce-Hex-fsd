package com.springboot.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}

	@GetMapping("/getAll")
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}

	@GetMapping("/get/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable int categoryId) {

		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));

	}

	@PutMapping("/update/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
		Category updatedCategory = categoryService.updateCategory(categoryId, category);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
	}
}
