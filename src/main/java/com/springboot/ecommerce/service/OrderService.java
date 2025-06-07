package com.springboot.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.ecommerce.dto.OrderHistoryDTO;
import com.springboot.ecommerce.enums.OrderItemStatus;
import com.springboot.ecommerce.model.Address;
import com.springboot.ecommerce.model.Cart;
import com.springboot.ecommerce.model.CartItem;
import com.springboot.ecommerce.model.Customer;
import com.springboot.ecommerce.model.OrderItem;
import com.springboot.ecommerce.model.Orders;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.repo.AddressRepository;
import com.springboot.ecommerce.repo.CartItemRepository;
import com.springboot.ecommerce.repo.CustomerRepository;
import com.springboot.ecommerce.repo.OrderItemRepository;
import com.springboot.ecommerce.repo.OrderRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;

import jakarta.persistence.criteria.Order;



@Service
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SellerProductRepository sellerProductRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    
    @Autowired
    OrderHistoryDTO orderHistoryDTO;

    public OrderService(CartService cartService, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        SellerProductRepository sellerProductRepository,
                        CustomerRepository customerRepository,
                        AddressRepository addressRepository,
                        CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.sellerProductRepository = sellerProductRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Orders placeOrder(String username, int addressId) {
        Customer customer = customerRepository.getCustomerByUsername(username);
                
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Cart cart = cartService.getCartByCustomerUsername(username);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            SellerProduct sp = cartItem.getSellerProduct();
            if (sp.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficent quntity for: " + sp.getProduct().getProductName());
            }
            sp.setStockQuantity(sp.getStockQuantity() - cartItem.getQuantity());
            sellerProductRepository.save(sp);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setSellerProduct(sp);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(sp.getPrice());
            orderItem.setStatus(OrderItemStatus.PENDING);
            orderItemRepository.save(orderItem);
        }

        // to clear cart items alone not cart
        cartItemRepository.deleteByCartId(cart.getId());

        return order;
    }
    public List<OrderHistoryDTO> getOrderHistory(String username) {
    	Customer customer=customerRepository.getCustomerByUsername(username);
        List<OrderItem>list= orderItemRepository.findByOrderCustomerId(customer.getId());
        return orderHistoryDTO.convertIntoOrderHistoryDTO(list);
    }
}
