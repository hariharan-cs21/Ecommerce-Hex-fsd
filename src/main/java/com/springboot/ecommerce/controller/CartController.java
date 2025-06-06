package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.CartItem;
import com.springboot.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/add")
	public CartItem addToCart(Principal principal, @RequestParam int sellerProductId, @RequestParam int quantity) {
		return cartService.addToCart(principal.getName(), sellerProductId, quantity);
	}

	@GetMapping("/items")
	public List<CartItem> getCartItems(Principal principal) {
		return cartService.getCartItems(principal.getName());
	}

	@DeleteMapping("/clear")
	public ResponseEntity<?> clearCart(Principal principal) {
		cartService.clearCart(principal.getName());
		Map<String,String> map = new HashMap<>();
		map.put("message","Cart cleared");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
}
