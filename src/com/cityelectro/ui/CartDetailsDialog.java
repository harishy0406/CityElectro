package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.model.OrderItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for viewing detailed cart information
 */
public class CartDetailsDialog extends Dialog {
    private Customer customer;

    // UI Components
    private Label titleLabel;
    private java.awt.List cartItemsList;
    private Label totalLabel;
    private Button closeBtn;

    public CartDetailsDialog(Frame parent, Customer customer) {
        super(parent, "Cart Details", true);
        this.customer = customer;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
        loadCartDetails();
    }

    private void initializeComponents() {
        titleLabel = new Label("Your Shopping Cart Details", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        cartItemsList = new List(15);
        cartItemsList.setFont(new Font("Courier New", Font.PLAIN, 12));

        totalLabel = new Label("", Label.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(Color.BLUE);

        closeBtn = new Button("Close");
        closeBtn.setBackground(Color.GRAY);
        closeBtn.setPreferredSize(new Dimension(100, 30));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        Panel titlePanel = new Panel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Cart items list
        Panel listPanel = new Panel(new BorderLayout());
        Label listTitle = new Label("Cart Items:", Label.CENTER);
        listTitle.setFont(new Font("Arial", Font.BOLD, 12));
        listPanel.add(listTitle, BorderLayout.NORTH);
        listPanel.add(cartItemsList, BorderLayout.CENTER);
        add(listPanel, BorderLayout.CENTER);

        // Bottom panel
        Panel bottomPanel = new Panel(new BorderLayout());

        // Total
        Panel totalPanel = new Panel(new FlowLayout());
        totalPanel.add(totalLabel);
        bottomPanel.add(totalPanel, BorderLayout.CENTER);

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
        setSize(650, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }

    private void loadCartDetails() {
        cartItemsList.removeAll();
        java.util.List<OrderItem> cart = customer.getCart();

        if (cart.isEmpty()) {
            cartItemsList.add("Your cart is empty.");
            totalLabel.setText("Total: Rs. 0.00");
        } else {
            cartItemsList.add("Product Name              | Qty | Unit Price | Total");
            cartItemsList.add("------------------------------------------------------");

            double total = 0.0;
            for (OrderItem item : cart) {
                String productName = item.getProduct().getName();
                // Truncate long names for display
                if (productName.length() > 20) {
                    productName = productName.substring(0, 17) + "...";
                }

                String line = String.format("%-22s | %2d  | Rs.%8.2f | Rs.%8.2f",
                                          productName,
                                          item.getQuantity(),
                                          item.getProduct().getPrice(),
                                          item.getTotalPrice());
                cartItemsList.add(line);
                total += item.getTotalPrice();
            }

            cartItemsList.add("------------------------------------------------------");
            totalLabel.setText("Total Amount: Rs. " + String.format("%.2f", total));
        }
    }
}
