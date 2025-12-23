package com.cityelectro.model;

/**
 * Base User class for the store management system
 */
public abstract class User {
    protected String name;
    protected String username;
    protected String password;

    // Constructor
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayDashboard();

    @Override
    public String toString() {
        return String.format("Name: %s | Username: %s", name, username);
    }
}
