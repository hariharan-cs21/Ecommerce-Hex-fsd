package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductRequest;
import com.springboot.ecommerce.service.ExecutiveService;
import com.springboot.ecommerce.service.ProductRequestService;

@RestController
@RequestMapping("/api/executive")
@CrossOrigin(origins = "http://localhost:5173")
public class ExecutiveController {

	@Autowired
	private ExecutiveService executiveService;
	@Autowired
	ProductRequestService productRequestService;

	@GetMapping("/get-one")
	public Executive getSellerById(Principal principal) {
		String username = principal.getName();
		return executiveService.getExecutiveByUsername(username);
	}

	@PostMapping("/register")
	public Executive insertSeller(@RequestBody Executive executive) {
		return executiveService.insertExecutive(executive);
	}

	@GetMapping("/requests/pending")
	public List<ProductRequest> getPendingRequests() {
		return productRequestService.getPendingProductRequests();
	}

	// update the new product status by executive
	@PutMapping("/requests/approve/{requestId}")
	public Product approveProductRequest(
			@PathVariable int requestId,
			Principal principal) {
		return productRequestService.approveProductRequest(requestId, principal.getName());
	}

}