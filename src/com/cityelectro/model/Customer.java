package com.cityelectro.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer class representing customer users with shopping capabilities
 */
public class Customer extends User {
    private List<OrderItem> cart;

    // Constructor
    public Customer(String name, String username, String password) {
        super(name, username, password);
        this.cart = new ArrayList<>();
    }

    // Getters
    public List<OrderItem> getCart() {
        return cart;
    }

    // Cart management methods
    public void addToCart(OrderItem item) {
        cart.add(item);
    }

    public void removeFromCart(OrderItem item) {
        cart.remove(item);
    }

    public void clearCart() {
        cart.clear();
    }

    public double getCartTotal() {
        double total = 0.0;
        for (OrderItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n=== CUSTOMER DASHBOARD ===");
        System.out.println("Welcome, " + name + "!");
        System.out.println("Role: Customer");
        System.out.println("==========================");
    }
}
