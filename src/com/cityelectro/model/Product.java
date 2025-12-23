package com.cityelectro.model;

/**
 * Product entity class representing electronic products in the store
 */
public class Product {
    private String productId;
    private String name;
    private String category;
    private double price;

    // Constructor
    public Product(String productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Category: %s | Price: Rs.%.2f",
                           productId, name, category, price);
    }
}
