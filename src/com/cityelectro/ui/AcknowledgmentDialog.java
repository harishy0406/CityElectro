package com.cityelectro.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Generic acknowledgment dialog for displaying success messages
 */
public class AcknowledgmentDialog extends Dialog {
    private Label messageLabel;
    private Button okButton;
    private boolean acknowledged = false;

    public AcknowledgmentDialog(Component parent, String title, String message) {
        super(getParentFrame(parent), title, true);
        initializeComponents(message);
        setupLayout();
        setupEventHandlers();
        configureDialog();
    }

    public AcknowledgmentDialog(Component parent, String message) {
        this(parent, "Success", message);
    }

    private static Frame getParentFrame(Component component) {
        if (component instanceof Frame) {
            return (Frame) component;
        } else {
            // Find the parent frame by walking up the component hierarchy
            Component current = component;
            while (current != null) {
                if (current instanceof Frame) {
                    return (Frame) current;
                }
                current = current.getParent();
            }
            return null; // Fallback
        }
    }

    private void initializeComponents(String message) {
        messageLabel = new Label(message, Label.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        okButton = new Button("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 12));
        okButton.setBackground(Color.GREEN);
        okButton.setForeground(Color.WHITE);
        okButton.setPreferredSize(new Dimension(80, 30));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Message area
        Panel messagePanel = new Panel(new BorderLayout());
        messagePanel.setPreferredSize(new Dimension(300, 80));

        // Add some padding
        Panel paddingPanel = new Panel();
        paddingPanel.setPreferredSize(new Dimension(20, 20));
        messagePanel.add(paddingPanel, BorderLayout.NORTH);
        messagePanel.add(paddingPanel, BorderLayout.SOUTH);
        messagePanel.add(paddingPanel, BorderLayout.EAST);
        messagePanel.add(paddingPanel, BorderLayout.WEST);

        messagePanel.add(messageLabel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.CENTER);

        // Button area
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acknowledged = true;
                dispose();
            }
        });

        // Handle Enter key
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    acknowledged = true;
                    dispose();
                }
            }
        });
    }

    private void configureDialog() {
        setSize(350, 150);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Make dialog focusable for key events
        setFocusable(true);
        okButton.requestFocus();
    }

    /**
     * Show the dialog and wait for acknowledgment
     * @return true if user acknowledged (clicked OK), false otherwise
     */
    public boolean showAndWait() {
        setVisible(true);
        return acknowledged;
    }

    /**
     * Static method to show an acknowledgment dialog
     * @param parent parent component (Frame or Dialog)
     * @param message message to display
     */
    public static void showMessage(Component parent, String message) {
        AcknowledgmentDialog dialog = new AcknowledgmentDialog(parent, message);
        dialog.showAndWait();
    }

    /**
     * Static method to show an acknowledgment dialog with custom title
     * @param parent parent component (Frame or Dialog)
     * @param title dialog title
     * @param message message to display
     */
    public static void showMessage(Component parent, String title, String message) {
        AcknowledgmentDialog dialog = new AcknowledgmentDialog(parent, title, message);
        dialog.showAndWait();
    }
}
