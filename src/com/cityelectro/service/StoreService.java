package com.cityelectro.service;

import com.cityelectro.model.Product;
import com.cityelectro.model.Order;
import com.cityelectro.model.OrderItem;
import com.cityelectro.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * StoreService handles product and order management operations
 */
public class StoreService {
    private List<Product> products;
    private List<Order> orders;
    private Scanner scanner;
    private int productIdCounter;
    private int orderIdCounter;

    // Constructor
    public StoreService() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.productIdCounter = 1;
        this.orderIdCounter = 1;

        // Initialize with some sample products
        initializeSampleProducts();
    }

    /**
     * Initialize the store with sample electronic products
     */
    private void initializeSampleProducts() {
        addProduct(new Product("P" + productIdCounter++, "Samsung Galaxy S23", "Smartphones", 95000.00));
        addProduct(new Product("P" + productIdCounter++, "iPhone 15 Pro", "Smartphones", 185000.00));
        addProduct(new Product("P" + productIdCounter++, "MacBook Air M2", "Laptops", 145000.00));
        addProduct(new Product("P" + productIdCounter++, "Dell XPS 13", "Laptops", 120000.00));
        addProduct(new Product("P" + productIdCounter++, "Sony WH-1000XM5", "Headphones", 45000.00));
        addProduct(new Product("P" + productIdCounter++, "AirPods Pro", "Headphones", 32000.00));
        addProduct(new Product("P" + productIdCounter++, "Samsung 55\" 4K TV", "Televisions", 125000.00));
        addProduct(new Product("P" + productIdCounter++, "LG 43\" OLED TV", "Televisions", 95000.00));
        addProduct(new Product("P" + productIdCounter++, "iPad Air", "Tablets", 65000.00));
        addProduct(new Product("P" + productIdCounter++, "Samsung Galaxy Tab S9", "Tablets", 85000.00));
    }

    // Product Management Methods

    /**
     * Add a new product to the store
     * @param product the product to add
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Add a new product interactively
     */
    public void addProductInteractively() {
        System.out.println("\n=== ADD NEW PRODUCT ===");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter product category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter product price (Rs.): ");
        double price = 0.0;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
            if (price <= 0) {
                System.out.println("Price must be greater than 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format!");
            return;
        }

        String productId = "P" + productIdCounter++;
        Product newProduct = new Product(productId, name, category, price);
        addProduct(newProduct);

        System.out.println("Product added successfully!");
        System.out.println(newProduct);
    }

    /**
     * Update an existing product
     */
    public void updateProduct() {
        System.out.println("\n=== UPDATE PRODUCT ===");
        displayAllProducts();

        System.out.print("Enter product ID to update: ");
        String productId = scanner.nextLine().trim();

        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("Current product details:");
        System.out.println(product);

        System.out.print("Enter new name (press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            product.setName(name);
        }

        System.out.print("Enter new category (press Enter to keep current): ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            product.setCategory(category);
        }

        System.out.print("Enter new price (press Enter to keep current): ");
        String priceStr = scanner.nextLine().trim();
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                if (price > 0) {
                    product.setPrice(price);
                } else {
                    System.out.println("Price must be greater than 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format!");
            }
        }

        System.out.println("Product updated successfully!");
        System.out.println(product);
    }

    /**
     * Remove a product from the store
     */
    public void removeProduct() {
        System.out.println("\n=== REMOVE PRODUCT ===");
        displayAllProducts();

        System.out.print("Enter product ID to remove: ");
        String productId = scanner.nextLine().trim();

        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("Product to remove:");
        System.out.println(product);

        System.out.print("Are you sure you want to remove this product? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("y") || confirmation.equals("yes")) {
            products.remove(product);
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Display all products
     */
    public void displayAllProducts() {
        System.out.println("\n=== AVAILABLE PRODUCTS ===");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("==========================");
    }

    /**
     * Find product by ID
     * @param productId the product ID to search for
     * @return Product if found, null otherwise
     */
    public Product findProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // Order Management Methods

    /**
     * Create a new order for a customer
     * @param customer the customer placing the order
     * @param cartItems the items in the customer's cart
     * @return the created order
     */
    public Order createOrder(Customer customer, List<OrderItem> cartItems) {
        String orderId = "ORD" + orderIdCounter++;
        Order order = new Order(orderId, customer.getName(), customer.getUsername());

        for (OrderItem item : cartItems) {
            order.addOrderItem(item);
        }

        orders.add(order);
        return order;
    }

    /**
     * Display all orders (for admin)
     */
    public void displayAllOrders() {
        System.out.println("\n=== CUSTOMER ORDERS ===");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order order : orders) {
            System.out.println(order.getOrderSummary());
            System.out.println("-----------------------------");
        }
    }

    /**
     * Get all products (for external access)
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return products;
    }

    /**
     * Get all orders (for external access)
     * @return list of all orders
     */
    public List<Order> getAllOrders() {
        return orders;
    }

    /**
     * Get orders for a specific customer
     * @param customerUsername the customer's username
     * @return list of orders for the customer
     */
    public List<Order> getOrdersByCustomer(String customerUsername) {
        return orders.stream()
            .filter(order -> order.getCustomerUsername().equals(customerUsername))
            .toList();
    }
}
