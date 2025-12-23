package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.model.Order;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for completing checkout and placing orders
 */
public class CheckoutDialog extends Dialog {
    private Customer customer;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private TextArea orderSummaryArea;
    private Label totalLabel;
    private Button confirmBtn;
    private Button cancelBtn;
    private Label statusLabel;

    public CheckoutDialog(Frame parent, Customer customer, StoreService storeService) {
        super(parent, "Checkout - Confirm Order", true);
        this.customer = customer;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
        loadOrderSummary();
    }

    private void initializeComponents() {
        titleLabel = new Label("Order Confirmation", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        orderSummaryArea = new TextArea("", 15, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        orderSummaryArea.setEditable(false);
        orderSummaryArea.setFont(new Font("Courier New", Font.PLAIN, 12));

        totalLabel = new Label("", Label.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(Color.BLUE);

        confirmBtn = new Button("Confirm Order");
        confirmBtn.setBackground(Color.GREEN);
        confirmBtn.setPreferredSize(new Dimension(120, 30));

        cancelBtn = new Button("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 30));

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setForeground(Color.RED);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        Panel titlePanel = new Panel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Order summary
        Panel summaryPanel = new Panel(new BorderLayout());
        Label summaryTitle = new Label("Order Summary:", Label.CENTER);
        summaryTitle.setFont(new Font("Arial", Font.BOLD, 12));
        summaryPanel.add(summaryTitle, BorderLayout.NORTH);
        summaryPanel.add(orderSummaryArea, BorderLayout.CENTER);

        Panel totalPanel = new Panel(new FlowLayout());
        totalPanel.add(totalLabel);
        summaryPanel.add(totalPanel, BorderLayout.SOUTH);

        add(summaryPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        Panel southPanel = new Panel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);

        // Status label
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        southPanel.add(statusPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmOrder();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void configureDialog() {
        setSize(750, 600);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }

    private void loadOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer: ").append(customer.getName()).append("\n");
        summary.append("Username: ").append(customer.getUsername()).append("\n\n");

        summary.append("Order Items:\n");
        summary.append("--------------------------------------------------\n");

        double total = 0.0;
        for (int i = 0; i < customer.getCart().size(); i++) {
            var item = customer.getCart().get(i);
            summary.append(String.format("%d. %s\n", i + 1, item.toString()));
            total += item.getTotalPrice();
        }

        summary.append("--------------------------------------------------\n");

        orderSummaryArea.setText(summary.toString());
        totalLabel.setText("Total Amount: Rs. " + String.format("%.2f", total));
    }

    private void confirmOrder() {
        try {
            // Create the order
            Order order = storeService.createOrder(customer, customer.getCart());

            // Clear the cart
            customer.clearCart();

            // Show success acknowledgment dialog
            AcknowledgmentDialog.showMessage(this, "Order Placed Successfully!",
                "Thank you for shopping with CityElectro!\n\n" +
                "Order ID: " + order.getOrderId() + "\n" +
                "Total Amount: Rs. " + String.format("%.2f", order.getTotalAmount()) + "\n\n" +
                "Your order has been placed successfully!");

            // Show order details
            StringBuilder confirmation = new StringBuilder(orderSummaryArea.getText());
            confirmation.append("\nOrder ID: ").append(order.getOrderId());
            confirmation.append("\nStatus: ").append(order.getStatus());
            confirmation.append("\n\nThank you for shopping with CityElectro!");
            orderSummaryArea.setText(confirmation.toString());

            // Disable confirm button
            confirmBtn.setEnabled(false);
            confirmBtn.setLabel("Order Placed");

            // Change cancel to close
            cancelBtn.setLabel("Close");

        } catch (Exception e) {
            statusLabel.setText("Error placing order. Please try again.");
            statusLabel.setForeground(Color.RED);
        }
    }
}
