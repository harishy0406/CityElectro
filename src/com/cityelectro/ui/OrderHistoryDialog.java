package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.model.Order;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dialog for viewing customer's order history
 */
public class OrderHistoryDialog extends Dialog {
    private Customer customer;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private java.awt.List ordersList;
    private Button closeBtn;
    private Label summaryLabel;

    public OrderHistoryDialog(Frame parent, Customer customer, StoreService storeService) {
        super(parent, "Order History", true);
        this.customer = customer;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
        loadOrderHistory();
    }

    private void initializeComponents() {
        titleLabel = new Label("Your Order History", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        ordersList = new java.awt.List(20);
        ordersList.setFont(new Font("Courier New", Font.PLAIN, 11));

        closeBtn = new Button("Close");
        closeBtn.setBackground(Color.GRAY);
        closeBtn.setPreferredSize(new Dimension(100, 30));

        summaryLabel = new Label("", Label.CENTER);
        summaryLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        Panel titlePanel = new Panel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Orders list
        Panel listPanel = new Panel(new BorderLayout());
        Label listTitle = new Label("Your Past Orders:", Label.CENTER);
        listTitle.setFont(new Font("Arial", Font.BOLD, 12));
        listPanel.add(listTitle, BorderLayout.NORTH);
        listPanel.add(ordersList, BorderLayout.CENTER);
        add(listPanel, BorderLayout.CENTER);

        // Bottom panel
        Panel bottomPanel = new Panel(new BorderLayout());

        // Summary
        Panel summaryPanel = new Panel(new FlowLayout());
        summaryPanel.add(summaryLabel);
        bottomPanel.add(summaryPanel, BorderLayout.CENTER);

        // Close button
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(closeBtn);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void configureDialog() {
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }

    private void loadOrderHistory() {
        ordersList.removeAll();
        List<Order> customerOrders = storeService.getOrdersByCustomer(customer.getUsername());

        if (customerOrders.isEmpty()) {
            ordersList.add("You haven't placed any orders yet.");
            summaryLabel.setText("Total Orders: 0 | Total Spent: Rs. 0.00");
        } else {
            double totalSpent = 0.0;

            for (Order order : customerOrders) {
                ordersList.add("=== ORDER " + order.getOrderId() + " ===");
                ordersList.add("Date: " + order.getOrderId().substring(3)); // Extract order number as pseudo-date
                ordersList.add("Status: " + order.getStatus());
                ordersList.add("Items:");

                // Show individual order items
                for (var item : order.getOrderItems()) {
                    ordersList.add("  - " + item.getProduct().getName() +
                                 " (Qty: " + item.getQuantity() +
                                 ") - Rs. " + String.format("%.2f", item.getTotalPrice()));
                }

                ordersList.add("Total Amount: Rs. " + String.format("%.2f", order.getTotalAmount()));
                ordersList.add(""); // Empty line for separation

                totalSpent += order.getTotalAmount();
            }

            summaryLabel.setText("Total Orders: " + customerOrders.size() +
                               " | Total Spent: Rs. " + String.format("%.2f", totalSpent));
        }

        summaryLabel.setForeground(Color.BLUE);
    }
}
