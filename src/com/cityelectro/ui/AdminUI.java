package com.cityelectro.ui;

import com.cityelectro.model.Admin;
import com.cityelectro.service.StoreService;
import java.util.Scanner;

/**
 * AdminUI handles the administrator dashboard and menu operations
 */
public class AdminUI {
    private Admin admin;
    private StoreService storeService;
    private Scanner scanner;

    // Constructor
    public AdminUI(Admin admin, StoreService storeService) {
        this.admin = admin;
        this.storeService = storeService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display and handle the admin dashboard
     */
    public void displayDashboard() {
        admin.displayDashboard();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    storeService.addProductInteractively();
                    break;
                case 2:
                    storeService.updateProduct();
                    break;
                case 3:
                    storeService.removeProduct();
                    break;
                case 4:
                    storeService.displayAllProducts();
                    break;
                case 5:
                    storeService.displayAllOrders();
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
     * Display the admin menu options
     */
    private void displayMenu() {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. Add New Product");
        System.out.println("2. Update Existing Product");
        System.out.println("3. Remove Product");
        System.out.println("4. View All Products");
        System.out.println("5. View Customer Orders");
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
}
