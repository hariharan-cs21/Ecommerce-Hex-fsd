package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Orders;
import com.springboot.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/place/{addressId}")
	public Orders placeOrder(Principal principal, @PathVariable int addressId) {
		return orderService.placeOrder(principal.getName(), addressId);
	}
	
	@GetMapping("/history")
	public List<?>getOrderHistory(Principal principal){
		 return orderService.getOrderHistory(principal.getName());
	}
}