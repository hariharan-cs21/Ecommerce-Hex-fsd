package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.dto.ProductDTO;
import com.springboot.ecommerce.dto.SellerProductDTO;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/create/{categoryId}")
	public Product createProduct(@RequestBody Product product, @PathVariable int categoryId, Principal principal) {
		return productService.createProduct(product, categoryId, principal.getName()); 
	}
	
	@GetMapping("/get/category/{categoryId}")
	public List<ProductDTO> getProductsByCategoryId(@PathVariable int categoryId) {
	    return productService.getProductsByCategoryId(categoryId);
	}


	

	

	
}
