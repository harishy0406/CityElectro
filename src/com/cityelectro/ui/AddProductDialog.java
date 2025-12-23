package com.cityelectro.ui;

import com.cityelectro.model.Product;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for adding new products to the store
 */
public class AddProductDialog extends Dialog {
    private AdminGUIDashboard parent;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private Label nameLabel;
    private TextField nameField;
    private Label categoryLabel;
    private TextField categoryField;
    private Label priceLabel;
    private TextField priceField;
    private Button addBtn;
    private Button cancelBtn;
    private Label statusLabel;

    public AddProductDialog(Frame parent, StoreService storeService) {
        super(parent, "Add New Product", true);
        this.parent = (AdminGUIDashboard) parent;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
    }

    private void initializeComponents() {
        titleLabel = new Label("Add New Product", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        nameLabel = new Label("Product Name:");
        nameField = new TextField(25);

        categoryLabel = new Label("Category:");
        categoryField = new TextField(25);

        priceLabel = new Label("Price (Rs.):");
        priceField = new TextField(25);

        addBtn = new Button("Add Product");
        addBtn.setBackground(Color.GREEN);
        addBtn.setPreferredSize(new Dimension(100, 30));

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

        // Form panel
        Panel formPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(categoryLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(categoryField, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(priceField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(addBtn);
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
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Handle Enter key in price field
        priceField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
    }

    private void configureDialog() {
        setSize(550, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String priceStr = priceField.getText().trim();

        // Validation
        if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty()) {
            showStatusMessage("All fields are required!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                showStatusMessage("Price must be greater than 0!");
                return;
            }

            // Create and add the product
            Product product = new Product("", name, category, price); // ID will be set by storeService
            storeService.addProduct(product);

            // Show acknowledgment
            AcknowledgmentDialog.showMessage(this, "Product Added Successfully!",
                "Product: " + name + "\n" +
                "Category: " + category + "\n" +
                "Price: Rs. " + String.format("%.2f", price) + "\n\n" +
                "The product has been added to inventory.");

            // Clear fields
            nameField.setText("");
            categoryField.setText("");
            priceField.setText("");

            // Close dialog after showing success message
            dispose();

        } catch (NumberFormatException e) {
            showStatusMessage("Invalid price format!");
        }
    }

    private void showStatusMessage(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
}
