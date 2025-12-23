package com.cityelectro.service;

import com.cityelectro.model.Admin;
import com.cityelectro.model.Customer;
import com.cityelectro.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AuthenticationService handles user authentication and registration
 */
public class AuthenticationService {
    private List<User> users;
    private Admin admin;
    private Scanner scanner;

    // Predefined admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String ADMIN_NAME = "System Administrator";

    // Constructor
    public AuthenticationService() {
        this.users = new ArrayList<>();
        this.admin = new Admin(ADMIN_NAME, ADMIN_USERNAME, ADMIN_PASSWORD);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Authenticate admin user
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin authenticateAdmin() {
        System.out.println("\n=== ADMIN LOGIN ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful!");
            return admin;
        } else {
            System.out.println("Invalid admin credentials!");
            return null;
        }
    }

    /**
     * Authenticate admin user with provided credentials (for GUI)
     * @param username the admin username
     * @param password the admin password
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin authenticateAdmin(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        } else {
            return null;
        }
    }

    /**
     * Register a new customer
     * @return Customer object if registration successful, null if username exists
     */
    public Customer registerCustomer() {
        System.out.println("\n=== CUSTOMER REGISTRATION ===");
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        // Check if username already exists
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists! Please choose a different username.");
            return null;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine().trim();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return null;
        }

        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long!");
            return null;
        }

        Customer newCustomer = new Customer(name, username, password);
        users.add(newCustomer);

        System.out.println("Registration successful! Welcome to CityElectro, " + name + "!");
        return newCustomer;
    }

    /**
     * Register a new customer with provided details (for GUI)
     * @param name the customer's full name
     * @param username the customer's username
     * @param password the customer's password
     * @param confirmPassword the password confirmation
     * @return Customer object if registration successful, null if validation fails or username exists
     */
    public Customer registerCustomer(String name, String username, String password, String confirmPassword) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        // Check if username already exists
        if (isUsernameTaken(username)) {
            return null;
        }

        if (password == null || password.length() < 6) {
            return null;
        }

        if (!password.equals(confirmPassword)) {
            return null;
        }

        Customer newCustomer = new Customer(name.trim(), username.trim(), password);
        users.add(newCustomer);

        return newCustomer;
    }

    /**
     * Authenticate customer user
     * @return Customer object if authentication successful, null otherwise
     */
    public Customer authenticateCustomer() {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                    System.out.println("Customer login successful! Welcome back, " + customer.getName() + "!");
                    return customer;
                }
            }
        }

        System.out.println("Invalid username or password!");
        return null;
    }

    /**
     * Authenticate customer user with provided credentials (for GUI)
     * @param username the customer username
     * @param password the customer password
     * @return Customer object if authentication successful, null otherwise
     */
    public Customer authenticateCustomer(String username, String password) {
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                    return customer;
                }
            }
        }
        return null;
    }

    /**
     * Check if username is already taken by existing users
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    private boolean isUsernameTaken(String username) {
        // Check against admin username
        if (admin.getUsername().equals(username)) {
            return true;
        }

        // Check against existing customers
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get all registered customers (for admin use)
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Customer) {
                customers.add((Customer) user);
            }
        }
        return customers;
    }
}
