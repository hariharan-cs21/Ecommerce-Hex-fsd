package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.dto.SellerOrderItemDTO;
import com.springboot.ecommerce.service.SellerOrderService;

@RestController
@RequestMapping("/api/seller/orders")
public class SellerOrderController {
	@Autowired
	private SellerOrderService sellerOrderService;

	@GetMapping("/get")
	public ResponseEntity<?> getSellerOrders(Principal principal, @RequestParam(required = false) String status) {
		List<SellerOrderItemDTO> orders = sellerOrderService.getSellerOrderView(principal.getName(), status);
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}
	
	@PutMapping("/update-status/{orderItemId}")
    public ResponseEntity<?> updateOrderItemStatus(@PathVariable int orderItemId, @RequestParam String status) {
        Map<String,String> map = new HashMap<>();
        map.put("message",sellerOrderService.updateOrderItemStatus(orderItemId, status));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}
