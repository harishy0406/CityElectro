package com.cityelectro.model;

/**
 * OrderItem entity class representing a product with quantity in an order
 */
public class OrderItem {
    private Product product;
    private int quantity;

    // Constructor
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calculate total price for this order item
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s | Qty: %d | Total: Rs.%.2f",
                           product.getName(), quantity, getTotalPrice());
    }
}
