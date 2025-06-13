package com.springboot.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecommerce.enums.OrderItemStatus;
import com.springboot.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	List<OrderItem> findByOrderCustomerId(int customerId);

	List<OrderItem> findByOrderId(int orderId);

	List<OrderItem> findByStatus(OrderItemStatus status);

	List<OrderItem> findBySellerProductSellerId(int sellerId);
	
//	
//	@Query("SELECT oi FROM OrderItem oi WHERE oi.status = ?1 AND oi.order.customer.id = ?2")
//	List<OrderItem> findByStatusAndCustomerId(OrderItemStatus status, int customerId);



}