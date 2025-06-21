package com.springboot.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.springboot.ecommerce.enums.OrderItemStatus;
import com.springboot.ecommerce.model.OrderItem;

@Component
public class OrderHistoryDTO {
	private int orderId;
	private LocalDateTime orderDate;
	private String status;
	private String shippingCity;
	private String street;
	private String contact;
	private double totalPrice;

	private List<OrderItemDetail> items = new ArrayList<>();

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderItemDetail> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDetail> items) {
		this.items = items;
	}

	public List<OrderHistoryDTO> convertIntoOrderHistoryDTO(List<OrderItem> orderItems) {
		Map<Integer, List<OrderItem>> grouped = orderItems.stream()
				.collect(Collectors.groupingBy(oi -> oi.getOrder().getId()));

		List<OrderHistoryDTO> result = new ArrayList<>();

		for (Map.Entry<Integer, List<OrderItem>> entry : grouped.entrySet()) {
			List<OrderItem> items = entry.getValue();
			OrderItem first = items.get(0); // all items belong to same order

			OrderHistoryDTO dto = new OrderHistoryDTO();
			dto.setOrderId(first.getOrder().getId());
			dto.setOrderDate(first.getOrder().getOrderDate());
			dto.setStatus(first.getOrder().getStatus());
			dto.setShippingCity(first.getOrder().getAddress().getCity());
			dto.setStreet(first.getOrder().getAddress().getStreet());
			dto.setContact(first.getOrder().getAddress().getContactNumber());

			double total = 0;
			List<OrderItemDetail> itemDetails = new ArrayList<>();

			for (OrderItem item : items) {
				OrderItemDetail detail = new OrderItemDetail();
				detail.setProductName(item.getSellerProduct().getProduct().getProductName());
				detail.setQuantity(item.getQuantity());
				detail.setPrice(item.getPrice());
				detail.setItemStatus(item.getStatus());
				detail.setItemId(item.getId());
				itemDetails.add(detail);
				total += item.getQuantity() * item.getPrice();
			}

			dto.setItems(itemDetails);
			dto.setTotalPrice(total);
			result.add(dto);
		}

		return result;
	}

	public static class OrderItemDetail {
		private OrderItemStatus itemStatus;

		private String productName;
		private int quantity;
		private double price;
		private int itemId;

		public OrderItemStatus getItemStatus() {
			return itemStatus;
		}

		public void setItemStatus(OrderItemStatus orderItemStatus) {
			this.itemStatus = orderItemStatus;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
	}
}
