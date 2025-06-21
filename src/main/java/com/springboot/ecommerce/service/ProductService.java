package com.springboot.ecommerce.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecommerce.dto.ProductDTO;
import com.springboot.ecommerce.dto.SellerProductDTO;
import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.model.Executive;
import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.SellerProduct;
import com.springboot.ecommerce.repo.CategoryRepository;
import com.springboot.ecommerce.repo.ExecutiveRepository;
import com.springboot.ecommerce.repo.ProductRepository;
import com.springboot.ecommerce.repo.SellerProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	private ExecutiveRepository executiveRepository;
	private SellerProductRepository sellerProductRepository;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			SellerProductRepository sellerProductRepository, ExecutiveRepository executiveRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.executiveRepository = executiveRepository;
		this.sellerProductRepository = sellerProductRepository;
	}

	public Product createProduct(Product product, int categoryId, String executiveUsername) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		Executive executive = executiveRepository.getExecutiveByUsername(executiveUsername);

		product.setCategory(category);
		product.setExecutive(executive);

		return productRepository.save(product);
	}

	public List<ProductDTO> getProductsByCategoryId(int categoryId) {
		List<Product> products = productRepository.findByCategoryId(categoryId);
		List<ProductDTO> productDTOs = new ArrayList<>();

		for (Product product : products) {
			// Only include product if at least one SellerProduct has stock
			List<SellerProduct> sellerProducts = sellerProductRepository.findByProductProductId(product.getProductId());

			boolean available = sellerProducts.stream()
					.anyMatch(sp -> sp.getStockQuantity() != null && sp.getStockQuantity() > 0);

			if (available) {
				ProductDTO dto = new ProductDTO(product.getProductId(), product.getBrandName(),
						product.getProductName(), product.getImageUrl());
				productDTOs.add(dto);
			}
		}

		return productDTOs;
	}

	public List<ProductDTO> getRandomProducts(int limit) {
		List<Product> allProducts = productRepository.findAll();
		Collections.shuffle(allProducts);

		List<ProductDTO> randomProducts = new ArrayList<>();

		for (Product product : allProducts) {
			ProductDTO dto = new ProductDTO(
					product.getProductId(),
					product.getBrandName(),
					product.getProductName(),
					product.getImageUrl());
			randomProducts.add(dto);

			if (randomProducts.size() >= limit) {
				break;
			}
		}

		return randomProducts;
	}

	public List<SellerProductDTO> getSellerProductsByProductId(int productId) {
		List<SellerProduct> sellerProducts = sellerProductRepository.findByProductProductId(productId);
		List<SellerProductDTO> dtoList = new ArrayList<>();

		for (SellerProduct sp : sellerProducts) {
			SellerProductDTO dto = new SellerProductDTO();
			dto.setProductId(sp.getProduct().getProductId());
			dto.setSellerProductId(sp.getId());
			dto.setImageUrl(sp.getProduct().getImageUrl());
			dto.setProductName(sp.getProduct().getProductName());
			dto.setBrandName(sp.getProduct().getBrandName());
			dto.setSellerName(sp.getSeller().getName());
			dto.setPrice(sp.getPrice());
			dto.setStockQuantity(sp.getStockQuantity());
			dtoList.add(dto);
		}

		return dtoList;
	}

	public Product uploadProfilePic(int productId, String name, MultipartFile file) throws IOException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		String originalFile = file.getOriginalFilename();
		String extension = originalFile.split("\\.")[1];

		if (!(List.of("jpg", "jpeg", "png", "svg", "gif").contains(extension.toLowerCase()))) {
			throw new RuntimeException(
					"Invalid File Type: " + extension + ", Upload only" + List.of("jpg", "jpeg", "png", "svg", "gif"));
		}
		String uploadFolder = "C:\\Users\\hari0\\Desktop\\Ecomm-React\\Ecomm-react\\public\\images";
		Files.createDirectories(Path.of(uploadFolder));
		Path path = Paths.get(uploadFolder, originalFile);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		product.setImageUrl(originalFile);

		return productRepository.save(product);
	}

}
