package com.springboot.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouse_dispatch")
public class WarehouseDispatch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int customerId;
	private LocalDateTime dispatchTime;

	@ManyToOne
	private WarehouseExecutive executive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(LocalDateTime dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public WarehouseExecutive getExecutive() {
		return executive;
	}

	public void setExecutive(WarehouseExecutive executive) {
		this.executive = executive;
	}

}
