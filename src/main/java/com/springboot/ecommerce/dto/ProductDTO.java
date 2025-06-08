package com.springboot.ecommerce.dto;


public class ProductDTO {

	 private int productId;
	    private String brandName;
	    private String productName;
	    private String imageUrl;

	    public ProductDTO(int productId, String brandName, String productName, String imageUrl) {
	        this.productId = productId;
	        this.brandName = brandName;
	        this.productName = productName;
	        this.imageUrl = imageUrl;
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

    
}
