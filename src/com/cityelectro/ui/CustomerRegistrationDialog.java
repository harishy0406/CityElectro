package com.cityelectro.ui;

import com.cityelectro.model.Customer;
import com.cityelectro.service.AuthenticationService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Customer registration dialog for new user signup
 */
public class CustomerRegistrationDialog extends Dialog {
    private MainGUI mainGUI;
    private AuthenticationService authService;

    // UI Components
    private Label titleLabel;
    private Label nameLabel;
    private TextField nameField;
    private Label usernameLabel;
    private TextField usernameField;
    private Label passwordLabel;
    private TextField passwordField;
    private Label confirmPasswordLabel;
    private TextField confirmPasswordField;
    private Button registerBtn;
    private Button cancelBtn;
    private Label statusLabel;

    public CustomerRegistrationDialog(Frame parent, AuthenticationService authService) {
        super(parent, "Customer Registration", true);
        this.mainGUI = (MainGUI) parent;
        this.authService = authService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
    }

    private void initializeComponents() {
        titleLabel = new Label("Customer Registration", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        nameLabel = new Label("Full Name:");
        nameField = new TextField(20);

        usernameLabel = new Label("Username:");
        usernameField = new TextField(20);

        passwordLabel = new Label("Password:");
        passwordField = new TextField(20);
        passwordField.setEchoChar('*');

        confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordField = new TextField(20);
        confirmPasswordField.setEchoChar('*');

        registerBtn = new Button("Register");
        registerBtn.setBackground(Color.GREEN);
        registerBtn.setPreferredSize(new Dimension(80, 30));

        cancelBtn = new Button("Cancel");
        cancelBtn.setPreferredSize(new Dimension(80, 30));

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

        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom panel with buttons and status
        Panel bottomPanel = new Panel(new BorderLayout());

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        // Status panel
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Handle Enter key in confirm password field
        confirmPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
    }

    private void configureDialog() {
        setSize(550, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void performRegistration() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validation
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showStatusMessage("All fields are required!");
            return;
        }

        if (password.length() < 6) {
            showStatusMessage("Password must be at least 6 characters long!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showStatusMessage("Passwords do not match!");
            return;
        }

        Customer newCustomer = authService.registerCustomer(name, username, password, confirmPassword);
        if (newCustomer != null) {
            // Show success acknowledgment dialog
            AcknowledgmentDialog.showMessage(this, "Registration Successful!",
                "Welcome to CityElectro, " + name + "!\nYour account has been created successfully.");

            // Close dialog after successful registration
            dispose();
        } else {
            showStatusMessage("Registration failed. Please check your details!");
        }
    }

    private void showStatusMessage(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
}
