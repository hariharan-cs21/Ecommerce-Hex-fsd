package com.springboot.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.springboot.ecommerce.enums.OrderItemStatus;

public class SellerOrderItemDTO {
	private String customerName;
	private String street;
	private String city;
	private String contactNumber;
	private LocalDateTime orderDateTime;

	private List<SellerProductDTO> products = new ArrayList<>();

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<SellerProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<SellerProductDTO> products) {
		this.products = products;
	}

	public static class SellerProductDTO {
		private String productName;
		private String brandName;
		private String category;
		private OrderItemStatus itemStatus;
		private int orderItemId;
		private int sellerProductId;

		public int getOrderItemId() {
			return orderItemId;
		}

		public void setOrderItemId(int orderItemId) {
			this.orderItemId = orderItemId;
		}

		public int getSellerProductId() {
			return sellerProductId;
		}

		public void setSellerProductId(int sellerProductId) {
			this.sellerProductId = sellerProductId;
		}

		private int quantity;
		private double price;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
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

		public OrderItemStatus getItemStatus() {
			return itemStatus;
		}

		public void setItemStatus(OrderItemStatus itemStatus) {
			this.itemStatus = itemStatus;
		}

	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
}
