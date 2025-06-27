package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Cart;
import com.springboot.ecommerce.model.CartItem;
import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.repo.CartItemRepository;
import com.springboot.ecommerce.repo.CartRepository;
import com.springboot.ecommerce.repo.CustomerRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	private Logger logger = LoggerFactory.getLogger(CartService.class);

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final CustomerRepository customerRepository;
	private final SellerProductRepository sellerProductRepository;

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			CustomerRepository customerRepository, SellerProductRepository sellerProductRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.customerRepository = customerRepository;
		this.sellerProductRepository = sellerProductRepository;
	}

	public Cart getCartByCustomerUsername(String username) {
		Customer customer = customerRepository.getCustomerByUsername(username);

		return cartRepository.findByCustomerId(customer.getId()).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setCustomer(customer);
			return cartRepository.save(newCart);
		});
	}

	public CartItem addToCart(String username, int sellerProductId, int quantity) {

		Cart cart = getCartByCustomerUsername(username);
		SellerProduct sp = sellerProductRepository.findById(sellerProductId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
		Optional<CartItem> existing = cartItems.stream()
				.filter(item -> item.getSellerProduct().getId() == sellerProductId)
				.findFirst();
		logger.info("Adding Seller-Product Id {} with quantity {} to cart for user {}", sellerProductId, quantity,
				username);

		if (existing.isPresent()) {
			CartItem item = existing.get();
			item.setQuantity(item.getQuantity() + quantity);
			return cartItemRepository.save(item);
		} else {
			CartItem item = new CartItem();
			item.setCart(cart);
			item.setSellerProduct(sp);
			item.setQuantity(quantity);
			return cartItemRepository.save(item);
		}
	}

	public List<CartItem> getCartItems(String username) {
		Cart cart = getCartByCustomerUsername(username);
		return cartItemRepository.findByCartId(cart.getId());
	}

	@Transactional
	public void clearCart(String username) {
		Cart cart = getCartByCustomerUsername(username);
		logger.warn("Clear cart for user: {}", username);
		cartItemRepository.deleteByCartId(cart.getId());
	}
}
