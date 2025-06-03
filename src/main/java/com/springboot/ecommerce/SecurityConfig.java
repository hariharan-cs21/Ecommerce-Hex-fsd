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
					//seller register
					.requestMatchers("/api/seller/register").permitAll()
				
					.requestMatchers("/api/user/details").authenticated()
					.requestMatchers("/getProductsBySellerId/{sellerID}").authenticated()
					
					//product add,modify
					.requestMatchers("/api/product/add/{productId}","/api/product/update/{sellerProductId}").hasAuthority("SELLER")
					.requestMatchers("/api/seller-product/request/{categoryId}","/api/seller-product/requests/seller").hasAuthority("SELLER")
					.requestMatchers("/api/seller/get-one").hasAuthority("SELLER")
					
					.requestMatchers("/api/category/add").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/product/create/{categoryId}").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/executive/requests/pending","/api/executive/requests/approve/{requestId}").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/executive/register").permitAll()
					.anyRequest().authenticated()
			)
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
