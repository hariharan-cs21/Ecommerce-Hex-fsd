package com.springboot.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.ecommerce.dto.ProductDTO;
import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.repo.CategoryRepository;
import com.springboot.ecommerce.repo.ExecutiveRepository;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;


@SpringBootTest
class ProductServiceTest {
	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private ExecutiveRepository executiveRepository;

	@Mock
	private SellerProductRepository sellerProductRepository;

	private Product product;
	private Category category;
	private Executive executive;

	@BeforeEach
	void init() {
		category = new Category();
		category.setId(1);
		category.setCategoryName("Electronics");

		executive = new Executive();
		executive.setId(1);
		executive.setName("admin123");

		product = new Product();
		product.setProductId(10);
		product.setProductName("Smartphone");
		product.setBrandName("Samsung");
		product.setImageUrl("image.jpg");
	}

	@Test
	void testCreateProduct() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
		when(executiveRepository.getExecutiveByUsername("admin123")).thenReturn(executive);
		when(productRepository.save(product)).thenReturn(product);

		Product result = productService.createProduct(product, 1, "admin123");

		assertEquals("Smartphone", result.getProductName());
	}

	@Test
	void testGetProductsByCategoryId_noStock() {
		when(productRepository.findByCategoryId(1)).thenReturn(List.of(product));
		when(sellerProductRepository.findByProductProductId(10)).thenReturn(Collections.emptyList());

		List<ProductDTO> result = productService.getProductsByCategoryId(1);

		assertTrue(result.isEmpty());
	}
	
	@AfterEach
	void end() {
		executive=null;
		category=null;
		product=null;
	}

}
