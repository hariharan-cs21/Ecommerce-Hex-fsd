package com.springboot.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;
import com.springboot.ecommerce.repo.SellerRepository;

@SpringBootTest
public class SellerProductServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerProductRepository sellerProductRepository;

    @InjectMocks
    private SellerProductService sellerProductService;

    private Seller seller;
    private Product product;
    private SellerProduct sellerProductData;
    private SellerProduct savedSellerProduct;

    @BeforeEach
    void init() {
        seller = new Seller();
        seller.setId(1);

        product = new Product();
        product.setProductId(101);

        sellerProductData = new SellerProduct();
        sellerProductData.setPrice(100.0);
        sellerProductData.setStockQuantity(50);

        savedSellerProduct = new SellerProduct();
        savedSellerProduct.setPrice(100.0);
        savedSellerProduct.setStockQuantity(50);
        savedSellerProduct.setProduct(product);
        savedSellerProduct.setSeller(seller);
    }

    @Test
    void testAddSellerProduct_Success() {
        when(sellerRepository.getSellerByUsername("hari")).thenReturn(seller);
        when(productRepository.findById(101)).thenReturn(Optional.of(product));
        when(sellerProductRepository.existsBySellerIdAndProductProductId(1, 101)).thenReturn(false);
        when(sellerProductRepository.save(any(SellerProduct.class))).thenReturn(savedSellerProduct);

        SellerProduct result = sellerProductService.addSellerProduct(101, sellerProductData, "hari");

        assertNotNull(result);
        assertEquals(100.0, result.getPrice());
        assertEquals(50, result.getStockQuantity());
        assertEquals(product, result.getProduct());
        assertEquals(seller, result.getSeller());
    }

    @Test
    void testAddSellerProduct_ProductNotFound() {
        when(sellerRepository.getSellerByUsername("hari")).thenReturn(seller);
        when(productRepository.findById(101)).thenReturn(Optional.empty());

        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            sellerProductService.addSellerProduct(101, sellerProductData, "hari");
        });
        assertEquals("Product not found", e.getMessage());

    }

    @Test
    void testAddSellerProduct_AlreadyExists() {
        when(sellerRepository.getSellerByUsername("hari")).thenReturn(seller);
        when(productRepository.findById(101)).thenReturn(Optional.of(product));
        when(sellerProductRepository.existsBySellerIdAndProductProductId(1, 101)).thenReturn(true);

        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            sellerProductService.addSellerProduct(101, sellerProductData, "hari");
        });
        assertEquals("Seller has already listed this product", e.getMessage());

    }

    @AfterEach
    void end() {
        seller = null;
        product = null;
        sellerProductData = null;
        savedSellerProduct = null;
    }
}
