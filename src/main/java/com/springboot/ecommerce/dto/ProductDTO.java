package com.springboot.ecommerce.dto;


import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.model.Seller;

public class ProductDTO {

    private int productId;
    private String brandName;
    private String productname;
    private double price;
    private Integer stockQuantity;
    private String imageUrl;
    private Category category; 
    private SellerDTO seller; 
    
    public ProductDTO(int productId, String brandName, String productname, double price, 
                      Integer stockQuantity, String imageUrl, Category category, SellerDTO seller) {
        this.productId = productId;
        this.brandName = brandName;
        this.productname = productname;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.seller = seller;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
