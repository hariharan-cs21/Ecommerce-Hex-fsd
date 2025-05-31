package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.dto.ProductDTO;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.service.ProductService;
import com.springboot.ecommerce.service.SellerService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private ProductService productService;
	private SellerService sellerService;
	public ProductController(ProductService productService, SellerService sellerService) {
		this.productService = productService;
		this.sellerService = sellerService;
	}



	
	@PostMapping("/add/{categoryId}")
	//Seller id need to get after using spring security
	
	public Product add(@RequestBody Product product,@PathVariable int categoryId,Principal principal) {
		String username = principal.getName();
	    Seller seller = sellerService.getSellerByUsername(username);
	    int sellerID = seller.getId();
		return productService.add(product,categoryId,sellerID);
	}
	
	@GetMapping("/getProductsBySellerId/{sellerID}")
	public List<Product> getProductsBySellerId(@PathVariable int sellerID) {
	    return productService.getProductsBySellerId(sellerID);
	}
	
	@GetMapping("/get/category/{categoryId}")
	public List<ProductDTO> getProductsByCategoryId(@PathVariable int categoryId) {
	    return productService.getProductsByCategoryId(categoryId);
	}

	
	
	 @PutMapping("/update/{productId}")
	    public Product update(@RequestBody Product product, 
	                          @PathVariable int productId) {
	        return productService.update(product, productId);
	    }
}
