package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.repo.CategoryRepository;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.SellerRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	private SellerRepository sellerRepository;
    private CategoryRepository categoryRepository;
	
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,SellerRepository sellerRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.sellerRepository=sellerRepository;
	}


	public Product add(Product product, int categoryId,int sellerId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
		product.setCategory(category);
		//seller object get after authentication only (spring security)
		Seller seller=sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
		product.setSeller(seller);
		return productRepository.save(product);
	}


	public Product update(Product updatedProduct, int productId) {
	    Product existingProduct = productRepository.findById(productId)
	        .orElseThrow(() -> new RuntimeException("Product not found"));

	    existingProduct.setBrandName(updatedProduct.getBrandName());
	    existingProduct.setProductname(updatedProduct.getProductname());
	    existingProduct.setPrice(updatedProduct.getPrice());
	    existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
	    existingProduct.setImageUrl(updatedProduct.getImageUrl());

	   
	    return productRepository.save(existingProduct);
	}


	public List<Product> getProductsBySellerId(int sellerID) {
	    return productRepository.findBySellerId(sellerID);
	}




}
