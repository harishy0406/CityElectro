package com.cityelectro.model;

/**
 * Admin class representing administrator users with full system access
 */
public class Admin extends User {

    // Constructor
    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("Welcome, " + name + "!");
        System.out.println("Role: Administrator");
        System.out.println("=======================");
    }
}
