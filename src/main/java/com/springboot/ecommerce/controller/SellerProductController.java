package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.dto.SellerProductDTO;
import com.springboot.ecommerce.model.ProductRequest;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.service.ProductRequestService;
import com.springboot.ecommerce.service.SellerProductService;

@RestController
@RequestMapping("/api/seller-product")

public class SellerProductController {
	@Autowired
	private final SellerProductService sellerProductService;
	@Autowired
	ProductRequestService productRequestService;
	
	public SellerProductController(SellerProductService sellerProductService) {
		this.sellerProductService = sellerProductService;
	}

	@PostMapping("/add/{productId}")
	public SellerProduct addSellerProduct(@PathVariable int productId, @RequestBody SellerProduct sellerProduct,
			Principal principal) {

		String sellerUsername = principal.getName();
		return sellerProductService.addSellerProduct(productId, sellerProduct, sellerUsername);
	}

	@GetMapping("/getProductsBySellerId/{sellerID}")
	public List<SellerProductDTO> getProductsBySellerId(@PathVariable int sellerID) {
		return sellerProductService.getSellerProducts(sellerID);
	}

	@PutMapping("/update/{sellerProductId}")
	public SellerProduct updateSellerProduct(@PathVariable int sellerProductId, @RequestBody SellerProduct updatedData,
			Principal principal) {
		String username = principal.getName();
		return sellerProductService.updateSellerProduct(sellerProductId, updatedData, username);
	}
	
	
	//requesting new product not availble in the portal
	@PostMapping("/request/{categoryId}")
	public ProductRequest createProductRequest(
	        @RequestBody ProductRequest request,
	        @PathVariable int categoryId,
	        Principal principal) {
	    return productRequestService.createProductRequest(request, categoryId, principal.getName());
	}
	
	//getting the new product requests
	@GetMapping("/requests/seller")
	public List<ProductRequest> getSellerProductRequests(Principal principal) {
	    return productRequestService.getRequestsBySeller(principal.getName());
	}


}
