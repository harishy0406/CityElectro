package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.model.Product;
import com.cityelectro.model.Order;
import com.cityelectro.model.OrderItem;
import com.cityelectro.service.StoreService;
import java.util.List;
import java.util.Scanner;

/**
 * CustomerUI handles the customer dashboard and shopping operations
 */
public class CustomerUI {
    private Customer customer;
    private StoreService storeService;
    private Scanner scanner;

    // Constructor
    public CustomerUI(Customer customer, StoreService storeService) {
        this.customer = customer;
        this.storeService = storeService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display and handle the customer dashboard
     */
    public void displayDashboard() {
        customer.displayDashboard();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    viewAvailableProducts();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    viewTotalPayment();
                    break;
                case 5:
                    confirmOrder();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (running) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    /**
     * Display the customer menu options
     */
    private void displayMenu() {
        System.out.println("\n=== CUSTOMER MENU ===");
        System.out.println("1. View Available Products");
        System.out.println("2. Place Order (Add to Cart)");
        System.out.println("3. View Cart");
        System.out.println("4. View Total Payment");
        System.out.println("5. Confirm Order");
        System.out.println("6. Logout");
        System.out.print("Enter your choice (1-6): ");
    }

    /**
     * Get user choice from input
     * @return the chosen menu option
     */
    private int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    /**
     * Display all available products for browsing
     */
    private void viewAvailableProducts() {
        storeService.displayAllProducts();
    }

    /**
     * Allow customer to add products to cart
     */
    private void placeOrder() {
        System.out.println("\n=== PLACE ORDER ===");
        storeService.displayAllProducts();

        System.out.print("Enter product ID to add to cart: ");
        String productId = scanner.nextLine().trim();

        Product product = storeService.findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("Selected product:");
        System.out.println(product);

        System.out.print("Enter quantity: ");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity!");
            return;
        }

        OrderItem orderItem = new OrderItem(product, quantity);
        customer.addToCart(orderItem);

        System.out.println("Product added to cart successfully!");
        System.out.println(orderItem);
    }

    /**
     * Display current cart contents
     */
    private void viewCart() {
        System.out.println("\n=== YOUR CART ===");
        List<OrderItem> cart = customer.getCart();

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        for (int i = 0; i < cart.size(); i++) {
            System.out.println((i + 1) + ". " + cart.get(i));
        }
        System.out.println("==========================");
    }

    /**
     * Display total payment amount
     */
    private void viewTotalPayment() {
        System.out.println("\n=== CART TOTAL ===");
        double total = customer.getCartTotal();
        System.out.printf("Total Amount: Rs.%.2f%n", total);

        if (customer.isCartEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in cart: " + customer.getCart().size());
        }
    }

    /**
     * Confirm and place the order
     */
    private void confirmOrder() {
        System.out.println("\n=== CONFIRM ORDER ===");

        if (customer.isCartEmpty()) {
            System.out.println("Your cart is empty. Add some products first!");
            return;
        }

        viewCart();
        viewTotalPayment();

        System.out.print("Do you want to confirm this order? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("y") || confirmation.equals("yes")) {
            // Create the order
            Order order = storeService.createOrder(customer, customer.getCart());

            System.out.println("Order placed successfully!");
            System.out.println("Order Details:");
            System.out.println(order.getOrderSummary());

            // Clear the cart after successful order
            customer.clearCart();
        } else {
            System.out.println("Order cancelled.");
        }
    }
}
