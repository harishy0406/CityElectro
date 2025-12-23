package com.cityelectro.ui;

import com.cityelectro.model.Admin;
import com.cityelectro.model.Product;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Admin GUI Dashboard for product and order management
 */
public class AdminGUIDashboard extends Frame {
    private MainGUI mainGUI;
    private Admin admin;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private Label welcomeLabel;
    private java.awt.List productList;
    private Button addProductBtn;
    private Button updateProductBtn;
    private Button removeProductBtn;
    private Button viewOrdersBtn;
    private Button logoutBtn;
    private Label statusLabel;

    public AdminGUIDashboard(MainGUI mainGUI, Admin admin, StoreService storeService) {
        this.mainGUI = mainGUI;
        this.admin = admin;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureWindow();
        loadProducts();
    }

    private void initializeComponents() {
        titleLabel = new Label("ADMIN DASHBOARD", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        welcomeLabel = new Label("Welcome, " + admin.getName() + "!", Label.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        productList = new List(15);
        productList.setFont(new Font("Courier New", Font.PLAIN, 12));

        addProductBtn = new Button("Add New Product");
        addProductBtn.setBackground(Color.GREEN);
        addProductBtn.setPreferredSize(new Dimension(150, 30));

        updateProductBtn = new Button("Update Product");
        updateProductBtn.setBackground(Color.ORANGE);
        updateProductBtn.setPreferredSize(new Dimension(150, 30));

        removeProductBtn = new Button("Remove Product");
        removeProductBtn.setBackground(Color.RED);
        removeProductBtn.setPreferredSize(new Dimension(150, 30));

        viewOrdersBtn = new Button("View Orders");
        viewOrdersBtn.setBackground(Color.BLUE);
        viewOrdersBtn.setForeground(Color.WHITE);
        viewOrdersBtn.setPreferredSize(new Dimension(150, 30));

        logoutBtn = new Button("Logout");
        logoutBtn.setBackground(Color.GRAY);
        logoutBtn.setPreferredSize(new Dimension(150, 30));

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setForeground(Color.BLUE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title panel
        Panel titlePanel = new Panel(new GridLayout(2, 1));
        titlePanel.add(titleLabel);
        titlePanel.add(welcomeLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main content panel
        Panel contentPanel = new Panel(new BorderLayout());

        // Products section
        Panel productsPanel = new Panel(new BorderLayout());
        Label productsTitle = new Label("Available Products:", Label.CENTER);
        productsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        productsPanel.add(productsTitle, BorderLayout.NORTH);
        productsPanel.add(productList, BorderLayout.CENTER);

        contentPanel.add(productsPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(350, 120));

        Panel btnRow1 = new Panel(new FlowLayout());
        btnRow1.add(addProductBtn);
        btnRow1.add(updateProductBtn);
        buttonPanel.add(btnRow1);

        Panel btnRow2 = new Panel(new FlowLayout());
        btnRow2.add(removeProductBtn);
        btnRow2.add(viewOrdersBtn);
        buttonPanel.add(btnRow2);

        Panel btnRow3 = new Panel(new FlowLayout());
        btnRow3.add(logoutBtn);
        buttonPanel.add(btnRow3);

        contentPanel.add(buttonPanel, BorderLayout.EAST);

        add(contentPanel, BorderLayout.CENTER);

        // Status panel
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddProductDialog();
            }
        });

        updateProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateProductDialog();
            }
        });

        removeProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedProduct();
            }
        });

        viewOrdersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrdersDialog();
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainGUI.setVisible(true);
            }
        });
    }

    private void configureWindow() {
        setTitle("CityElectro - Admin Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void loadProducts() {
        productList.removeAll();
        java.util.List<Product> products = storeService.getAllProducts();

        if (products.isEmpty()) {
            productList.add("No products available.");
        } else {
            for (Product product : products) {
                productList.add(product.toString());
            }
        }

        showStatusMessage("Loaded " + products.size() + " products", false);
    }

    private void openAddProductDialog() {
        AddProductDialog dialog = new AddProductDialog(this, storeService);
        dialog.setVisible(true);
        loadProducts(); // Refresh the list
    }

    private void openUpdateProductDialog() {
        String selectedItem = productList.getSelectedItem();
        if (selectedItem == null || selectedItem.equals("No products available.")) {
            showStatusMessage("Please select a product to update!", true);
            return;
        }

        // Extract product ID from the selected string
        String productId = selectedItem.split(" \\| ")[0].substring(4); // Remove "ID: " prefix
        Product product = storeService.findProductById(productId);

        if (product != null) {
            UpdateProductDialog dialog = new UpdateProductDialog(this, storeService, product);
            dialog.setVisible(true);
            loadProducts(); // Refresh the list
        }
    }

    private void removeSelectedProduct() {
        String selectedItem = productList.getSelectedItem();
        if (selectedItem == null || selectedItem.equals("No products available.")) {
            showStatusMessage("Please select a product to remove!", true);
            return;
        }

        // Extract product ID from the selected string
        String productId = selectedItem.split(" \\| ")[0].substring(4); // Remove "ID: " prefix

        // Show confirmation dialog
        RemoveProductDialog dialog = new RemoveProductDialog(this, storeService, productId);
        dialog.setVisible(true);
        loadProducts(); // Refresh the list
    }

    private void openOrdersDialog() {
        OrdersDialog dialog = new OrdersDialog(this, storeService);
        dialog.setVisible(true);
    }

    public void showStatusMessage(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Color.RED : Color.BLUE);
    }
}
