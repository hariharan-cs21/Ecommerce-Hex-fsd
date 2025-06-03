package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductRequest;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.repo.CategoryRepository;
import com.springboot.ecommerce.repo.ExecutiveRepository;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.ProductRequestRepository;
import com.springboot.ecommerce.repo.SellerRepository;

@Service
public class ProductRequestService {
	private ProductRepository productRepository;
	private SellerRepository sellerRepository;
	private ProductRequestRepository productRequestRepository;
	private CategoryRepository categoryRepository;
	private ExecutiveRepository executiveRepository;

	

	public ProductRequestService(ProductRepository productRepository, SellerRepository sellerRepository,
			ProductRequestRepository productRequestRepository, CategoryRepository categoryRepository,
			ExecutiveRepository executiveRepository) {
		this.productRepository = productRepository;
		this.sellerRepository = sellerRepository;
		this.productRequestRepository = productRequestRepository;
		this.categoryRepository = categoryRepository;
		this.executiveRepository = executiveRepository;
	}

	// Seller submits a product request
	public ProductRequest createProductRequest(ProductRequest request, int categoryId, String sellerUsername) {
	    Seller seller = sellerRepository.getSellerByUsername(sellerUsername);
	    Category category = categoryRepository.findById(categoryId)
	        .orElseThrow(() -> new RuntimeException("Category not found"));

	    request.setSeller(seller);
	    request.setCategory(category);
	    request.setApproved(false);
	    return productRequestRepository.save(request);
	}
	//seller gets his product request
	public List<ProductRequest> getRequestsBySeller(String sellerUsername) {
	    return productRequestRepository.findBySellerUserUsername(sellerUsername);
	}

	//executive gets all pending requests
	public List<ProductRequest> getPendingProductRequests() {
	    return productRequestRepository.findByApprovedFalse();
	}

	public Product approveProductRequest(int requestId, String executiveUsername) {
	    ProductRequest req = productRequestRepository.findById(requestId)
	        .orElseThrow(() -> new RuntimeException("Request not found"));

	    if (req.isApproved()) throw new RuntimeException("Already approved");

	    Executive executive = executiveRepository.getExecutiveByUsername(executiveUsername);

	    Product product = new Product();
	    product.setBrandName(req.getBrandName());
	    product.setProductName(req.getProductName());
	    product.setImageUrl(req.getImageUrl());
	    product.setCategory(req.getCategory());
	    product.setExecutive(executive);

	    product = productRepository.save(product);

	    req.setApproved(true);
	    productRequestRepository.save(req);

	    return product;
	}

}
