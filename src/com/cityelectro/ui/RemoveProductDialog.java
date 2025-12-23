package com.cityelectro.ui;

import com.cityelectro.model.Product;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for confirming product removal
 */
public class RemoveProductDialog extends Dialog {
    private AdminGUIDashboard parent;
    private StoreService storeService;
    private String productId;

    // UI Components
    private Label titleLabel;
    private Label messageLabel;
    private Label productInfoLabel;
    private Button removeBtn;
    private Button cancelBtn;

    public RemoveProductDialog(Frame parent, StoreService storeService, String productId) {
        super(parent, "Remove Product", true);
        this.parent = (AdminGUIDashboard) parent;
        this.storeService = storeService;
        this.productId = productId;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
        loadProductInfo();
    }

    private void initializeComponents() {
        titleLabel = new Label("Confirm Product Removal", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        messageLabel = new Label("Are you sure you want to remove this product?", Label.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        productInfoLabel = new Label("", Label.CENTER);
        productInfoLabel.setFont(new Font("Courier New", Font.BOLD, 12));

        removeBtn = new Button("Remove Product");
        removeBtn.setBackground(Color.RED);
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setPreferredSize(new Dimension(120, 30));

        cancelBtn = new Button("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 30));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        Panel titlePanel = new Panel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Content panel
        Panel contentPanel = new Panel(new GridLayout(2, 1, 0, 10));
        contentPanel.add(messageLabel);
        contentPanel.add(productInfoLabel);
        add(contentPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(removeBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeProduct();
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
        setSize(500, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void loadProductInfo() {
        Product product = storeService.findProductById(productId);
        if (product != null) {
            productInfoLabel.setText(product.toString());
            productInfoLabel.setForeground(Color.BLUE);
        } else {
            productInfoLabel.setText("Product not found!");
            productInfoLabel.setForeground(Color.RED);
            removeBtn.setEnabled(false);
        }
    }

    private void removeProduct() {
        Product product = storeService.findProductById(productId);
        if (product != null) {
            storeService.getAllProducts().remove(product);

            // Show acknowledgment
            AcknowledgmentDialog.showMessage(this, "Product Removed Successfully!",
                "Product: " + product.getName() + "\n" +
                "ID: " + product.getProductId() + "\n\n" +
                "The product has been removed from inventory.");

            parent.showStatusMessage("Product removed successfully!", false);
        }
        dispose();
    }
}
