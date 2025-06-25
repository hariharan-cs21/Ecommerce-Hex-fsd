package com.springboot.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	@Autowired
	private JwtFilter jwtFilter;

	/*
	 * Executive, customer routes not done
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/user/signup").permitAll()
						.requestMatchers("/api/user/details").permitAll()
						.requestMatchers("/api/user/token").permitAll()
						// seller register
						.requestMatchers("/api/seller/register").permitAll()
						.requestMatchers("/api/customer/register").permitAll()
						.requestMatchers("/api/product/random", "/api/product/{productId}/sellers").permitAll()

						.requestMatchers("/api/address/list", "/api/address/add").permitAll()
						.requestMatchers("/api/cart/add", "/api/cart/items", "/api/cart/clear").permitAll()
						.requestMatchers("/api/order/history").permitAll()
						.requestMatchers("/api/order/place/{addressId}", "/api/order/cancel/{orderId}").permitAll()
						.requestMatchers("/api/warehouse/dispatch-times/order/{orderId}").permitAll()
						.requestMatchers("/api/category/getAll").permitAll()
						.requestMatchers("/api/product/get/category/{categoryId}").permitAll()
						.requestMatchers("/api/seller/get-one").hasAuthority("SELLER")
						.requestMatchers("/api/seller/orders/get").permitAll()
						.requestMatchers("/api/seller/orders/update-status/{orderItemId}").permitAll()
						.requestMatchers("/api/product/upload-pic/{productId}").permitAll()
						.requestMatchers("/getProductsBySellerId/{sellerID}").authenticated()
						.requestMatchers("/api/address/delete/{id}", "/api/address/update/{id}").permitAll()
						.requestMatchers("/api/product/add/{productId}", "/api/product/update/{sellerProductId}")
						.hasAuthority("SELLER")
						.requestMatchers("/api/seller-product/getProductsBySellerId/{sellerID}",
								"/api/seller-product/update/{sellerProductId}", "/api/seller-product/add/{productId}",
								"/api/seller-product/getProductsBySeller/{productId}",
								"/api/seller-product/getProductsOfSeller")
						.permitAll()
						.requestMatchers("/api/seller-product/request/{categoryId}",
								"/api/seller-product/requests/seller", "/api/seller-product/getStock/{sellerProductId}")
						.permitAll()

						.requestMatchers("/api/warehouse/shipped", "/api/warehouse/dispatch/{orderId}",
								"/api/warehouse/deliver", "/api/warehouse/dispatched")
						.permitAll()

						.requestMatchers("/api/category/add", "/api/category/update/{categoryId}").permitAll()

						.requestMatchers("/api/executive/requests/pending",
								"/api/executive/requests/approve/{requestId}")
						.permitAll()
						.requestMatchers("/api/product/create/{categoryId}").permitAll()
						.requestMatchers("/api/user/getSellers").permitAll()

						.requestMatchers("/api/executive/register").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth)
			throws Exception {
		return auth.getAuthenticationManager();
	}
}
