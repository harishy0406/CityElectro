package com.cityelectro.ui;

import com.cityelectro.model.Admin;
import com.cityelectro.service.AuthenticationService;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Admin login dialog for authentication
 */
public class AdminLoginDialog extends Dialog {
    private MainGUI mainGUI;
    private AuthenticationService authService;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private Label usernameLabel;
    private TextField usernameField;
    private Label passwordLabel;
    private TextField passwordField;
    private Button loginBtn;
    private Button cancelBtn;
    private Label statusLabel;

    public AdminLoginDialog(Frame parent, AuthenticationService authService, StoreService storeService) {
        super(parent, "Admin Login", true);
        this.mainGUI = (MainGUI) parent;
        this.authService = authService;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureDialog();
    }

    private void initializeComponents() {
        titleLabel = new Label("Administrator Login", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        usernameLabel = new Label("Username:");
        usernameField = new TextField(20);

        passwordLabel = new Label("Password:");
        passwordField = new TextField(20);
        passwordField.setEchoChar('*'); // Hide password characters

        loginBtn = new Button("Login");
        loginBtn.setBackground(Color.GREEN);
        loginBtn.setPreferredSize(new Dimension(80, 30));

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

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Status panel (initially empty)
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Handle Enter key in password field
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void configureDialog() {
        setSize(450, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            return;
        }

        Admin admin = authService.authenticateAdmin(username, password);
        if (admin != null) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(Color.GREEN);

            // Open admin dashboard
            dispose();
            AdminGUIDashboard adminGUI = new AdminGUIDashboard(mainGUI, admin, storeService);
            adminGUI.setVisible(true);
        } else {
            statusLabel.setText("Invalid admin credentials!");
            statusLabel.setForeground(Color.RED);
        }
    }
}
