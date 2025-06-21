package com.springboot.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecommerce.dto.ProductDTO;
import com.springboot.ecommerce.dto.SellerProductDTO;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
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

	@GetMapping("/random")
	public ResponseEntity<List<ProductDTO>> getRandomProducts(@RequestParam(defaultValue = "5") int count) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getRandomProducts(count));
	}

	// get sellers for each product
	@GetMapping("/{productId}/sellers")
	public ResponseEntity<List<SellerProductDTO>> getSellerProducts(@PathVariable int productId) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getSellerProductsByProductId(productId));
	}

	@PutMapping("/upload-pic/{productId}")
	public Product uploadProfilePic(@PathVariable int productId, Principal principal,
			@RequestParam MultipartFile file) throws IOException {
		return productService.uploadProfilePic(productId, principal.getName(), file);

	}

}
