package com.springboot.ecommerce.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.dto.WarehouseDispatchDTO;
import com.springboot.ecommerce.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "http://localhost:5173")
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;

	@PostMapping("/dispatch/{orderId}")
	public ResponseEntity<?> dispatch(@PathVariable int orderId, Principal principal) {
		warehouseService.dispatchOrdersByOrderd(principal.getName(), orderId);
		return ResponseEntity.status(HttpStatus.OK).body("Dispatched Items for order id: " + orderId);
	}

	@PostMapping("/deliver")
	public ResponseEntity<?> deliverItems(@RequestBody List<Integer> orderItemIds) {
		warehouseService.markItemsAsDelivered(orderItemIds);
		return ResponseEntity.status(HttpStatus.OK).body("Marked as delivered");
	}

	@GetMapping("/shipped")
	public ResponseEntity<List<WarehouseDispatchDTO>> getShippedItems() {
		return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getShippedItems());
	}

	@GetMapping("/dispatch-times/order/{orderId}")
	public ResponseEntity<?> getDispatchTimes(
			Principal principal,
			@PathVariable int orderId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(warehouseService.getDispatchTimestamps(principal.getName(), orderId));
	}

}
