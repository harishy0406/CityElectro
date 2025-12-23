package com.cityelectro.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Order entity class representing a customer order
 */
public class Order {
    private String orderId;
    private String customerName;
    private String customerUsername;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private String status;

    // Constructor
    public Order(String orderId, String customerName, String customerUsername) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerUsername = customerUsername;
        this.orderItems = new ArrayList<>();
        this.totalAmount = 0.0;
        this.status = "Placed";
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Add order item and recalculate total
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        recalculateTotal();
    }

    // Remove order item and recalculate total
    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        recalculateTotal();
    }

    // Recalculate total amount based on order items
    private void recalculateTotal() {
        totalAmount = 0.0;
        for (OrderItem item : orderItems) {
            totalAmount += item.getTotalPrice();
        }
    }

    // Get order summary
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(String.format("Order ID: %s\n", orderId));
        summary.append(String.format("Customer: %s (%s)\n", customerName, customerUsername));
        summary.append(String.format("Status: %s\n", status));
        summary.append("Items:\n");

        for (OrderItem item : orderItems) {
            summary.append(String.format("  - %s\n", item.toString()));
        }

        summary.append(String.format("Total Amount: Rs.%.2f\n", totalAmount));
        return summary.toString();
    }

    @Override
    public String toString() {
        return String.format("Order %s - %s - Rs.%.2f (%s)",
                           orderId, customerName, totalAmount, status);
    }
}
