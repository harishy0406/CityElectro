package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.model.Product;
import com.cityelectro.model.OrderItem;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Customer GUI Dashboard for shopping and cart management
 */
public class CustomerGUIDashboard extends Frame {
    private MainGUI mainGUI;
    private Customer customer;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private Label welcomeLabel;
    private java.awt.List productsList;
    private java.awt.List cartList;
    private Label cartTotalLabel;
    private Button addToCartBtn;
    private Button removeFromCartBtn;
    private Button viewCartBtn;
    private Button checkoutBtn;
    private Button orderHistoryBtn;
    private Button logoutBtn;
    private Label statusLabel;

    public CustomerGUIDashboard(MainGUI mainGUI, Customer customer, StoreService storeService) {
        this.mainGUI = mainGUI;
        this.customer = customer;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureWindow();
        loadProducts();
        updateCartDisplay();
    }

    private void initializeComponents() {
        titleLabel = new Label("CUSTOMER DASHBOARD", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        welcomeLabel = new Label("Welcome, " + customer.getName() + "!", Label.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        productsList = new List(15);
        productsList.setFont(new Font("Courier New", Font.PLAIN, 11));

        cartList = new List(10);
        cartList.setFont(new Font("Courier New", Font.PLAIN, 11));

        cartTotalLabel = new Label("Cart Total: Rs. 0.00", Label.CENTER);
        cartTotalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cartTotalLabel.setForeground(Color.BLUE);

        addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setBackground(Color.GREEN);
        addToCartBtn.setPreferredSize(new Dimension(120, 30));

        removeFromCartBtn = new Button("Remove from Cart");
        removeFromCartBtn.setBackground(Color.ORANGE);
        removeFromCartBtn.setPreferredSize(new Dimension(140, 30));

        viewCartBtn = new Button("View Cart Details");
        viewCartBtn.setBackground(Color.BLUE);
        viewCartBtn.setForeground(Color.WHITE);
        viewCartBtn.setPreferredSize(new Dimension(140, 30));

        checkoutBtn = new Button("Checkout");
        checkoutBtn.setBackground(Color.RED);
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setPreferredSize(new Dimension(100, 30));

        orderHistoryBtn = new Button("Order History");
        orderHistoryBtn.setBackground(Color.BLUE);
        orderHistoryBtn.setForeground(Color.WHITE);
        orderHistoryBtn.setPreferredSize(new Dimension(120, 30));

        logoutBtn = new Button("Logout");
        logoutBtn.setBackground(Color.GRAY);
        logoutBtn.setPreferredSize(new Dimension(100, 30));

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
        productsPanel.add(productsList, BorderLayout.CENTER);

        // Cart section
        Panel cartPanel = new Panel(new BorderLayout());
        Label cartTitle = new Label("Your Cart:", Label.CENTER);
        cartTitle.setFont(new Font("Arial", Font.BOLD, 14));
        cartPanel.add(cartTitle, BorderLayout.NORTH);

        Panel cartContentPanel = new Panel(new BorderLayout());
        cartContentPanel.add(cartList, BorderLayout.CENTER);

        Panel cartTotalPanel = new Panel(new FlowLayout());
        cartTotalPanel.add(cartTotalLabel);
        cartContentPanel.add(cartTotalPanel, BorderLayout.SOUTH);

        cartPanel.add(cartContentPanel, BorderLayout.CENTER);

        // Split products and cart
        Panel centerPanel = new Panel(new GridLayout(1, 2, 10, 0));
        centerPanel.add(productsPanel);
        centerPanel.add(cartPanel);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new GridLayout(4, 2, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(300, 120));

        Panel btnRow1 = new Panel(new FlowLayout());
        btnRow1.add(addToCartBtn);
        btnRow1.add(removeFromCartBtn);
        buttonPanel.add(btnRow1);

        Panel btnRow2 = new Panel(new FlowLayout());
        btnRow2.add(viewCartBtn);
        btnRow2.add(checkoutBtn);
        buttonPanel.add(btnRow2);

        Panel btnRow3 = new Panel(new FlowLayout());
        btnRow3.add(orderHistoryBtn);
        buttonPanel.add(btnRow3);

        Panel btnRow4 = new Panel(new FlowLayout());
        btnRow4.add(logoutBtn);
        buttonPanel.add(btnRow4);

        contentPanel.add(buttonPanel, BorderLayout.EAST);

        add(contentPanel, BorderLayout.CENTER);

        // Status panel
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        addToCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        removeFromCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromCart();
            }
        });

        viewCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCartDetailsDialog();
            }
        });

        checkoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCheckoutDialog();
            }
        });

        orderHistoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrderHistoryDialog();
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
        setTitle("CityElectro - Customer Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void loadProducts() {
        productsList.removeAll();
        java.util.List<Product> products = storeService.getAllProducts();

        if (products.isEmpty()) {
            productsList.add("No products available.");
        } else {
            for (Product product : products) {
                productsList.add(product.toString());
            }
        }
    }

    private void updateCartDisplay() {
        cartList.removeAll();
        java.util.List<OrderItem> cart = customer.getCart();

        if (cart.isEmpty()) {
            cartList.add("Cart is empty");
        } else {
            for (OrderItem item : cart) {
                cartList.add(item.toString());
            }
        }

        cartTotalLabel.setText("Cart Total: Rs. " + String.format("%.2f", customer.getCartTotal()));
    }

    private void addToCart() {
        String selectedProduct = productsList.getSelectedItem();
        if (selectedProduct == null || selectedProduct.equals("No products available.")) {
            showStatusMessage("Please select a product to add to cart!", true);
            return;
        }

        // Extract product ID
        String productId = selectedProduct.split(" \\| ")[0].substring(4); // Remove "ID: " prefix
        Product product = storeService.findProductById(productId);

        if (product != null) {
            // Ask for quantity
            QuantityDialog qtyDialog = new QuantityDialog(this, "Add to Cart - " + product.getName());
            qtyDialog.setVisible(true);

            int quantity = qtyDialog.getQuantity();
            if (quantity > 0) {
                OrderItem orderItem = new OrderItem(product, quantity);
                customer.addToCart(orderItem);
                updateCartDisplay();

                // Show acknowledgment
                AcknowledgmentDialog.showMessage(this, "Item Added to Cart",
                    product.getName() + " (Quantity: " + quantity + ")\n" +
                    "has been added to your cart successfully!");

                showStatusMessage("Added to cart: " + product.getName(), false);
            }
        }
    }

    private void removeFromCart() {
        String selectedItem = cartList.getSelectedItem();
        if (selectedItem == null || selectedItem.equals("Cart is empty")) {
            showStatusMessage("Please select an item to remove from cart!", true);
            return;
        }

        // Find the order item in cart
        java.util.List<OrderItem> cart = customer.getCart();
        for (OrderItem item : cart) {
            if (selectedItem.contains(item.getProduct().getName())) {
                customer.removeFromCart(item);
                updateCartDisplay();

                // Show acknowledgment
                AcknowledgmentDialog.showMessage(this, "Item Removed from Cart",
                    item.getProduct().getName() + "\n" +
                    "has been removed from your cart.");

                showStatusMessage("Removed from cart: " + item.getProduct().getName(), false);
                return;
            }
        }
    }

    private void openCartDetailsDialog() {
        CartDetailsDialog dialog = new CartDetailsDialog(this, customer);
        dialog.setVisible(true);
        updateCartDisplay();
    }

    private void openCheckoutDialog() {
        if (customer.isCartEmpty()) {
            showStatusMessage("Your cart is empty! Add some products first.", true);
            return;
        }

        CheckoutDialog dialog = new CheckoutDialog(this, customer, storeService);
        dialog.setVisible(true);
        updateCartDisplay();
    }

    private void openOrderHistoryDialog() {
        OrderHistoryDialog dialog = new OrderHistoryDialog(this, customer, storeService);
        dialog.setVisible(true);
    }

    public void showStatusMessage(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Color.RED : Color.BLUE);
    }
}
