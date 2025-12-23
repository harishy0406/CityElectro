package com.cityelectro.ui;

import com.cityelectro.model.Order;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for viewing all customer orders
 */
public class OrdersDialog extends Dialog {
    private AdminGUIDashboard parent;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private java.awt.List ordersList;
    private Button closeBtn;
    private Label summaryLabel;

    public OrdersDialog(Frame parent, StoreService storeService) {
        super(parent, "Customer Orders", true);
        this.parent = (AdminGUIDashboard) parent;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
        loadOrders();
    }

    private void initializeComponents() {
        titleLabel = new Label("Customer Orders", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        ordersList = new List(20);
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
        Label listTitle = new Label("All Orders:", Label.CENTER);
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

    private void loadOrders() {
        ordersList.removeAll();
        java.util.List<Order> orders = storeService.getAllOrders();

        if (orders.isEmpty()) {
            ordersList.add("No orders found.");
            summaryLabel.setText("Total Orders: 0 | Total Revenue: Rs. 0.00");
        } else {
            double totalRevenue = 0.0;

            for (Order order : orders) {
                ordersList.add("=== ORDER " + order.getOrderId() + " ===");
                ordersList.add("Customer: " + order.getCustomerName() + " (" + order.getCustomerUsername() + ")");
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

                totalRevenue += order.getTotalAmount();
            }

            summaryLabel.setText("Total Orders: " + orders.size() +
                               " | Total Revenue: Rs. " + String.format("%.2f", totalRevenue));
        }

        summaryLabel.setForeground(Color.BLUE);
    }
}
