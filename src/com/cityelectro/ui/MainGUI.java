package com.cityelectro.ui;

import com.cityelectro.model.Admin;
import com.cityelectro.model.Customer;
import com.cityelectro.service.AuthenticationService;
import com.cityelectro.service.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Main GUI window for CityElectro - provides login and registration options
 */
public class MainGUI extends Frame {
    private AuthenticationService authService;
    private StoreService storeService;

    // UI Components
    private Label titleLabel;
    private Label subtitleLabel;
    private Button adminLoginBtn;
    private Button customerLoginBtn;
    private Button customerRegisterBtn;
    private Button exitBtn;
    private Label statusLabel;

    public MainGUI(AuthenticationService authService, StoreService storeService) {
        this.authService = authService;
        this.storeService = storeService;

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureWindow();
    }

    private void initializeComponents() {
        // Title
        titleLabel = new Label("CITYELECTRO - SMART ELECTRONICS STORE", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        subtitleLabel = new Label("Fast. Reliable. Digital Electronics Shopping.", Label.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // Buttons
        adminLoginBtn = new Button("Admin Login");
        adminLoginBtn.setBackground(Color.LIGHT_GRAY);
        adminLoginBtn.setPreferredSize(new Dimension(200, 40));

        customerLoginBtn = new Button("Customer Login");
        customerLoginBtn.setBackground(Color.LIGHT_GRAY);
        customerLoginBtn.setPreferredSize(new Dimension(200, 40));

        customerRegisterBtn = new Button("Customer Registration");
        customerRegisterBtn.setBackground(Color.LIGHT_GRAY);
        customerRegisterBtn.setPreferredSize(new Dimension(200, 40));

        exitBtn = new Button("Exit");
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setPreferredSize(new Dimension(200, 40));

        // Status label
        statusLabel = new Label("", Label.CENTER);
        statusLabel.setForeground(Color.BLUE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title panel
        Panel titlePanel = new Panel(new GridLayout(2, 1));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main menu panel
        Panel menuPanel = new Panel(new GridLayout(4, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(250, 200));

        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(adminLoginBtn);
        menuPanel.add(buttonPanel);

        buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(customerLoginBtn);
        menuPanel.add(buttonPanel);

        buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(customerRegisterBtn);
        menuPanel.add(buttonPanel);

        buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(exitBtn);
        menuPanel.add(buttonPanel);

        add(menuPanel, BorderLayout.CENTER);

        // Status panel
        Panel statusPanel = new Panel(new FlowLayout());
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);

        // Add some padding
        Panel paddingPanel = new Panel();
        paddingPanel.setPreferredSize(new Dimension(50, 50));
        add(paddingPanel, BorderLayout.EAST);
        add(paddingPanel, BorderLayout.WEST);
    }

    private void setupEventHandlers() {
        adminLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginDialog();
            }
        });

        customerLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerLoginDialog();
            }
        });

        customerRegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerRegistrationDialog();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void configureWindow() {
        setTitle("CityElectro - Electronics Store Management System");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setVisible(true);

        // Handle window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        // Create a gradient background
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Create gradient from blue to light blue
        GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180),
                                                 width, height, new Color(135, 206, 235));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Draw some electronic-themed elements
        g2d.setColor(new Color(255, 255, 255, 100));

        // Draw circuit-like patterns
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < width; i += 50) {
            g2d.drawLine(i, 0, i + 20, 20);
            g2d.drawLine(i + 20, 20, i + 40, 20);
        }

        // Draw some electronic symbols
        g2d.setColor(new Color(255, 255, 255, 150));
        g2d.setStroke(new BasicStroke(3));

        // Draw a resistor symbol
        int centerX = width / 2;
        int centerY = height / 2;
        g2d.drawLine(centerX - 30, centerY, centerX - 10, centerY);
        g2d.drawRect(centerX - 10, centerY - 5, 20, 10);
        g2d.drawLine(centerX + 10, centerY, centerX + 30, centerY);

        // Draw some circuit nodes
        g2d.fillOval(centerX - 35, centerY - 3, 6, 6);
        g2d.fillOval(centerX + 29, centerY - 3, 6, 6);

        super.paint(g);
    }

    private void openAdminLoginDialog() {
        AdminLoginDialog loginDialog = new AdminLoginDialog(this, authService, storeService);
        loginDialog.setVisible(true);
    }

    private void openCustomerLoginDialog() {
        CustomerLoginDialog loginDialog = new CustomerLoginDialog(this, authService, storeService);
        loginDialog.setVisible(true);
    }

    private void openCustomerRegistrationDialog() {
        CustomerRegistrationDialog registerDialog = new CustomerRegistrationDialog(this, authService);
        registerDialog.setVisible(true);
    }

    public void showStatusMessage(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Color.RED : Color.BLUE);
    }

    public void clearStatusMessage() {
        statusLabel.setText("");
    }
}
