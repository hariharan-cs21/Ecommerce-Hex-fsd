package com.springboot.ecommerce.dto;

public class SellerProductDTO {
    private int productId;
    private String brandName;
    private String productName;
    private String imageUrl;
    private double price;
    private String SellerName;
    private Integer stockQuantity;
    private int sellerProductId;

    public SellerProductDTO() {
    }

    public SellerProductDTO(int productId, String brandName, String productName, String imageUrl, double price,
            Integer stockQuantity) {
        this.productId = productId;
        this.brandName = brandName;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stockQuantity = stockQuantity;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public int getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

}
