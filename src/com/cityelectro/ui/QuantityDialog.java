package com.cityelectro.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for entering quantity when adding items to cart
 */
public class QuantityDialog extends Dialog {
    private int quantity = 0;
    private boolean confirmed = false;

    // UI Components
    private Label titleLabel;
    private Label quantityLabel;
    private TextField quantityField;
    private Button okBtn;
    private Button cancelBtn;
    private Label statusLabel;

    public QuantityDialog(Frame parent, String title) {
        super(parent, title, true);

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
    }

    private void initializeComponents() {
        titleLabel = new Label("Enter Quantity", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        quantityLabel = new Label("Quantity:");
        quantityField = new TextField("1", 10);

        okBtn = new Button("OK");
        okBtn.setBackground(Color.GREEN);
        okBtn.setPreferredSize(new Dimension(80, 30));

        cancelBtn = new Button("Cancel");
        cancelBtn.setPreferredSize(new Dimension(80, 30));

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setForeground(Color.RED);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        Panel titlePanel = new Panel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input panel
        Panel inputPanel = new Panel(new FlowLayout());
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        add(inputPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(okBtn);
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
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmQuantity();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantity = 0;
                dispose();
            }
        });

        // Handle Enter key
        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmQuantity();
            }
        });
    }

    private void configureDialog() {
        setSize(350, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void confirmQuantity() {
        try {
            int qty = Integer.parseInt(quantityField.getText().trim());
            if (qty <= 0) {
                showStatusMessage("Quantity must be greater than 0!");
                return;
            }
            if (qty > 99) {
                showStatusMessage("Maximum quantity is 99!");
                return;
            }

            quantity = qty;
            confirmed = true;
            dispose();

        } catch (NumberFormatException e) {
            showStatusMessage("Please enter a valid number!");
        }
    }

    private void showStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
