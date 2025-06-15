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
						.requestMatchers("/api/user/details").authenticated()
						// seller register
						.requestMatchers("/api/seller/register").permitAll()
						.requestMatchers("/api/customer/register").permitAll()
						.requestMatchers("/api/product/random", "/api/product/{productId}/sellers").permitAll()

						.requestMatchers("/api/address/list", "/api/address/add").hasAuthority("CUSTOMER")
						.requestMatchers("/api/cart/add", "/api/cart/items", "/api/cart/clear").hasAuthority("CUSTOMER")
						.requestMatchers("/api/order/history").hasAuthority("CUSTOMER")

						.requestMatchers("/api/seller/get-one").hasAuthority("SELLER")
						.requestMatchers("/api/seller/orders/get").hasAuthority("SELLER")
						.requestMatchers("/getProductsBySellerId/{sellerID}").authenticated()

						.requestMatchers("/api/product/add/{productId}", "/api/product/update/{sellerProductId}")
						.hasAuthority("SELLER")
						.requestMatchers("/api/seller-product/request/{categoryId}",
								"/api/seller-product/requests/seller")
						.hasAuthority("SELLER")

						.requestMatchers("/api/warehouse/shipped").authenticated()

						.requestMatchers("/api/category/add").hasAuthority("EXECUTIVE")
						.requestMatchers("/api/product/create/{categoryId}").hasAuthority("EXECUTIVE")
						.requestMatchers("/api/executive/requests/pending",
								"/api/executive/requests/approve/{requestId}")
						.hasAuthority("EXECUTIVE")
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
