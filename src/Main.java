import com.cityelectro.model.Admin;
import com.cityelectro.model.Customer;
import com.cityelectro.service.AuthenticationService;
import com.cityelectro.service.StoreService;
import com.cityelectro.ui.AdminUI;
import com.cityelectro.ui.CustomerUI;
import java.util.Scanner;

/**
 * Main class - Entry point for CityElectro Smart Electronics Store Management System
 */
public class Main {
    private static AuthenticationService authService;
    private static StoreService storeService;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialize services
        authService = new AuthenticationService();
        storeService = new StoreService();
        scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   CITYELECTRO - SMART ELECTRONICS STORE");
        System.out.println("          Fast. Reliable. Digital.");
        System.out.println("=========================================");
        System.out.println("   Electronics Store Management System");
        System.out.println("         Located in Galle, Sri Lanka");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleCustomerRegistration();
                    break;
                case 3:
                    handleCustomerLogin();
                    break;
                case 4:
                    System.out.println("\nThank you for using CityElectro!");
                    System.out.println("Have a great day!");
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

        scanner.close();
    }

    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Admin Login");
        System.out.println("2. Customer Registration");
        System.out.println("3. Customer Login");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    /**
     * Get user choice from input
     * @return the chosen menu option
     */
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    /**
     * Handle admin login process
     */
    private static void handleAdminLogin() {
        Admin admin = authService.authenticateAdmin();
        if (admin != null) {
            AdminUI adminUI = new AdminUI(admin, storeService);
            adminUI.displayDashboard();
        }
    }

    /**
     * Handle customer registration process
     */
    private static void handleCustomerRegistration() {
        Customer customer = authService.registerCustomer();
        if (customer != null) {
            // After successful registration, show customer dashboard
            CustomerUI customerUI = new CustomerUI(customer, storeService);
            customerUI.displayDashboard();
        }
    }

    /**
     * Handle customer login process
     */
    private static void handleCustomerLogin() {
        Customer customer = authService.authenticateCustomer();
        if (customer != null) {
            CustomerUI customerUI = new CustomerUI(customer, storeService);
            customerUI.displayDashboard();
        }
    }
}
