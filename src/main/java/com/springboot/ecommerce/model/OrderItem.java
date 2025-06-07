package com.springboot.ecommerce.model;

import com.springboot.ecommerce.enums.OrderItemStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Orders order;

	@ManyToOne
	private SellerProduct sellerProduct;

	private int quantity;

	private double price;
	
	@Enumerated(EnumType.STRING)
	private OrderItemStatus status;

	public OrderItemStatus getStatus() {
		return status;
	}

	public void setStatus(OrderItemStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public SellerProduct getSellerProduct() {
		return sellerProduct;
	}

	public void setSellerProduct(SellerProduct sellerProduct) {
		this.sellerProduct = sellerProduct;
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
	
}
