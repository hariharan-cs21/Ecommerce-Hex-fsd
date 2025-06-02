package com.springboot.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.dto.SellerProductDTO;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.Seller;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;
import com.springboot.ecommerce.repo.SellerRepository;

@Service
public class SellerProductService {

	private SellerRepository sellerRepository;
	private ProductRepository productRepository;
	private SellerProductRepository sellerProductRepository;

	public SellerProductService(SellerRepository sellerRepository, ProductRepository productRepository,
			SellerProductRepository sellerProductRepository) {
		this.sellerRepository = sellerRepository;
		this.productRepository = productRepository;
		this.sellerProductRepository = sellerProductRepository;
	}

	public SellerProduct addSellerProduct(int productId, SellerProduct sellerProductData, String sellerUsername) {
		Seller seller = sellerRepository.getSellerByUsername(sellerUsername);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		boolean exists = sellerProductRepository.existsBySellerIdAndProductProductId(seller.getId(), productId);
		if (exists) {
			throw new RuntimeException("Seller has already listed this product.");
		}

		SellerProduct sellerProduct = new SellerProduct();
		sellerProduct.setProduct(product);
		sellerProduct.setSeller(seller);
		sellerProduct.setPrice(sellerProductData.getPrice());
		sellerProduct.setStockQuantity(sellerProductData.getStockQuantity());

		return sellerProductRepository.save(sellerProduct);
	}

	public List<SellerProductDTO> getSellerProducts(int sellerId) {
		List<SellerProduct> sellerProducts = sellerProductRepository.findBySellerId(sellerId);
		List<SellerProductDTO> dtoList = new ArrayList<>();

		for (SellerProduct sp : sellerProducts) {
			Product p = sp.getProduct();
			dtoList.add(new SellerProductDTO(p.getProductId(), p.getBrandName(), p.getProductName(), p.getImageUrl(),
					sp.getPrice(), sp.getStockQuantity()));
		}

		return dtoList;
	}

	public SellerProduct updateSellerProduct(int id, SellerProduct updatedData, String username) {
	    SellerProduct existing = sellerProductRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("SellerProduct not found"));

	    Seller seller = sellerRepository.getSellerByUsername(username);
	    if (existing.getSeller().getId() != seller.getId()) {
	        throw new RuntimeException("You are not authorized to update this product.");
	    }

	    existing.setStockQuantity(updatedData.getStockQuantity());
	    existing.setPrice(updatedData.getPrice());

	    return sellerProductRepository.save(existing);
	}

}
