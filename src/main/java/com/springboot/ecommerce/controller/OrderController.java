package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Orders;
import com.springboot.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:5173")

public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/place/{addressId}")
	public Orders placeOrder(Principal principal, @PathVariable int addressId) {
		return orderService.placeOrder(principal.getName(), addressId);
	}

	@GetMapping("/history")
	public List<?> getOrderHistory(Principal principal) {
		return orderService.getOrderHistory(principal.getName());
	}

	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable int orderId, Principal principal) {
		orderService.cancelOrderByCustomer(orderId, principal.getName());
		Map<String, String> mp = new HashMap<>();
		mp.put("message", "Order cancelled successfully");
		return ResponseEntity.status(HttpStatus.OK).body(mp);
	}

}