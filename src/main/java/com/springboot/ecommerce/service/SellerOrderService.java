package com.springboot.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.dto.SellerOrderItemDTO;
import com.springboot.ecommerce.enums.OrderItemStatus;
import com.springboot.ecommerce.model.OrderItem;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.repo.OrderItemRepository;
import com.springboot.ecommerce.repo.SellerRepository;

@Service
public class SellerOrderService {
	private OrderItemRepository orderItemRepository;
	private SellerRepository sellerRepository;

	public SellerOrderService(OrderItemRepository orderItemRepository, SellerRepository sellerRepository) {
		this.orderItemRepository = orderItemRepository;
		this.sellerRepository = sellerRepository;
	}

	public List<SellerOrderItemDTO> getSellerOrderView(String username, String statusFilter) {
		Seller seller = sellerRepository.getSellerByUsername(username);
		List<OrderItem> items = orderItemRepository.findBySellerProductSellerId(seller.getId());
		if (statusFilter != null) {
			OrderItemStatus.valueOf(statusFilter.toUpperCase());
		}
		if (statusFilter != null && !statusFilter.isEmpty()) {
			items = items.stream().filter(oi -> oi.getStatus().name().equalsIgnoreCase(statusFilter))
					.collect(Collectors.toList());
		}

		Map<Integer, List<OrderItem>> grouped = items.stream()
				.collect(Collectors.groupingBy(oi -> oi.getOrder().getId()));

		List<SellerOrderItemDTO> result = new ArrayList<>();

		for (List<OrderItem> group : grouped.values()) {
			OrderItem first = group.get(0);

			SellerOrderItemDTO dto = new SellerOrderItemDTO();
			dto.setCustomerName(first.getOrder().getCustomer().getName());
			dto.setStreet(first.getOrder().getAddress().getStreet());
			dto.setCity(first.getOrder().getAddress().getCity());
			dto.setContactNumber(first.getOrder().getAddress().getContactNumber());

			List<SellerOrderItemDTO.SellerProductDTO> productList = new ArrayList<>();
			for (OrderItem item : group) {
				SellerOrderItemDTO.SellerProductDTO prod = new SellerOrderItemDTO.SellerProductDTO();
				prod.setProductName(item.getSellerProduct().getProduct().getProductName());
				prod.setBrandName(item.getSellerProduct().getProduct().getBrandName());
				prod.setCategory(item.getSellerProduct().getProduct().getCategory().getCategoryName());
				prod.setQuantity(item.getQuantity());
				prod.setPrice(item.getPrice());
				prod.setItemStatus(item.getStatus());
				prod.setOrderItemId(item.getId());
				prod.setSellerProductId(item.getSellerProduct().getId());
				productList.add(prod);
			}

			dto.setProducts(productList);
			result.add(dto);
		}

		return result;
	}

	public String updateOrderItemStatus(int orderItemId, String statusStr) {
		OrderItem item = orderItemRepository.findById(orderItemId).orElse(null);

		if (item == null)
			return "Order Item not found";

		try {
			OrderItemStatus newStatus = OrderItemStatus.valueOf(statusStr.toUpperCase());

			if (newStatus != OrderItemStatus.SHIPPED && newStatus != OrderItemStatus.APPROVED) {
				return "Not allowed";
			}

			if (item.getStatus() == OrderItemStatus.PENDING || item.getStatus() == OrderItemStatus.APPROVED) {
				item.setStatus(newStatus);
				orderItemRepository.save(item);
				return "Order item status updated to " + newStatus;
			} else {
				return "Only PENDING items can be updated.";
			}

		} catch (IllegalArgumentException e) {
			return "Invalid status";
		}
	}

}
