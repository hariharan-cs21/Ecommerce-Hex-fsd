package com.springboot.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.dto.WarehouseDispatchDTO;
import com.springboot.ecommerce.enums.OrderItemStatus;
import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.model.OrderItem;
import com.springboot.ecommerce.model.Orders;
import com.springboot.ecommerce.model.WarehouseDispatch;
import com.springboot.ecommerce.model.WarehouseExecutive;
import com.springboot.ecommerce.repo.CustomerRepository;
import com.springboot.ecommerce.repo.OrderItemRepository;
import com.springboot.ecommerce.repo.OrderRepository;
import com.springboot.ecommerce.repo.WarehouseDispatchRepository;
import com.springboot.ecommerce.repo.WarehouseExecutiveRepository;

import jakarta.transaction.Transactional;

@Service
public class WarehouseService {
	@Autowired
	private OrderItemRepository orderItemRepo;
	@Autowired
	private WarehouseExecutiveRepository execRepo;
	@Autowired
	private WarehouseDispatchRepository dispatchRepo;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	public void dispatchOrdersByOrderd(String username, int orderId) {
		Orders order=orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		Customer customer=customerRepository.findById(order.getCustomer().getId()).orElseThrow(() -> new RuntimeException("Customer details not available"));
		WarehouseExecutive executive = execRepo.getByUserUsername(username);

		List<OrderItem> orderItems = orderItemRepo.findByOrderId(orderId);

		boolean allApproved = orderItems.stream().allMatch(item -> item.getStatus() == OrderItemStatus.SHIPPED);
		boolean isDispatched = orderItems.stream().allMatch(item -> item.getStatus() == OrderItemStatus.DISPATCHED);
		if(isDispatched) throw new RuntimeException("Already Dispatched");
		if (!allApproved) {
			throw new RuntimeException("Cannot dispatch. Not all items are SHIPPED for this order");
		}

		WarehouseDispatch dispatch = new WarehouseDispatch();
		dispatch.setExecutive(executive);
		dispatch.setCustomerId(customer.getId());
		dispatch.setDispatchTime(LocalDateTime.now());
		dispatchRepo.save(dispatch);

		for (OrderItem item : orderItems) {
			item.setDispatch(dispatch);
			item.setStatus(OrderItemStatus.DISPATCHED);
			orderItemRepo.save(item);
		}

	}

	@Transactional
	public void markItemsAsDelivered(List<Integer> orderItemIds) {
		List<OrderItem> items = orderItemRepo.findAllById(orderItemIds);

		for (OrderItem item : items) {
			if (item.getStatus() == OrderItemStatus.DISPATCHED) {
				item.setStatus(OrderItemStatus.DELIVERED);
				orderItemRepo.save(item);
			}
		}

		Set<Orders> ordersToCheck = items.stream().map(OrderItem::getOrder).collect(Collectors.toSet());

		for (Orders order : ordersToCheck) {
			List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId());

			boolean allDelivered = orderItems.stream().allMatch(i -> i.getStatus() == OrderItemStatus.DELIVERED);

			if (allDelivered) {
				order.setStatus("DELIVERED");
				orderRepository.save(order);
			}
		}
	}

	public List<WarehouseDispatchDTO> getShippedItems() {
		List<OrderItem> shippedItems = orderItemRepo.findByStatus(OrderItemStatus.SHIPPED);
		List<WarehouseDispatchDTO> result = new ArrayList<>();

		for (OrderItem item : shippedItems) {
			WarehouseDispatchDTO dto = new WarehouseDispatchDTO();
			dto.setOrderId(item.getOrder().getId());
			dto.setOrderItemId(item.getId());
			dto.setProductName(item.getSellerProduct().getProduct().getProductName());
			dto.setQuantity(item.getQuantity());

			dto.setCustomerName(item.getOrder().getCustomer().getName());
			dto.setContact(item.getOrder().getAddress().getContactNumber());
			dto.setCity(item.getOrder().getAddress().getCity());
			dto.setStreet(item.getOrder().getAddress().getStreet());

			result.add(dto);
		}

		return result;
	}

}
