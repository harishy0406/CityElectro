package com.cityelectro.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Landing page with background image and enter button
 */
public class LandingPage extends Frame {
    private BufferedImage backgroundImage;
    private Button enterButton;
    private MainGUI mainGUI;

    public LandingPage(MainGUI mainGUI) {
        this.mainGUI = mainGUI;

        loadBackgroundImage();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureWindow();
    }

    private void loadBackgroundImage() {
        try {
            // Try to load the background image
            File imageFile = new File("images/bg.png");
            if (imageFile.exists()) {
                backgroundImage = ImageIO.read(imageFile);
            } else {
                // Create a default background if image not found
                createDefaultBackground();
            }
        } catch (IOException e) {
            // Create a default background if image loading fails
            createDefaultBackground();
        }
    }

    private void createDefaultBackground() {
        // Create a default gradient background similar to MainGUI
        backgroundImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = backgroundImage.createGraphics();

        // Create gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180),
                                                 800, 600, new Color(135, 206, 235));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Add some text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        String title = "CityElectro";
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (800 - titleWidth) / 2, 200);

        g2d.setFont(new Font("Arial", Font.ITALIC, 24));
        fm = g2d.getFontMetrics();
        String subtitle = "Smart Electronics Store";
        int subtitleWidth = fm.stringWidth(subtitle);
        g2d.drawString(subtitle, (800 - subtitleWidth) / 2, 250);

        g2d.dispose();
    }

    private void initializeComponents() {
        enterButton = new Button("ENTER STORE");
        enterButton.setFont(new Font("Arial", Font.BOLD, 18));
        enterButton.setBackground(new Color(46, 204, 113)); // Green color
        enterButton.setForeground(Color.WHITE);
        enterButton.setPreferredSize(new Dimension(200, 50));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Custom panel to display background image with centered button
        Panel imagePanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (backgroundImage != null) {
                    // Scale image to fit the panel
                    Image scaledImage = backgroundImage.getScaledInstance(
                        getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, 0, 0, this);
                }

                // Draw button overlay in center of image
                if (enterButton != null) {
                    int buttonWidth = 200;
                    int buttonHeight = 50;
                    int buttonX = (getWidth() - buttonWidth) / 2;
                    int buttonY = (getHeight() - buttonHeight) / 2;

                    // Draw button background
                    g.setColor(enterButton.getBackground());
                    g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

                    // Draw button border
                    g.setColor(Color.BLACK);
                    g.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

                    // Draw button text
                    g.setColor(enterButton.getForeground());
                    g.setFont(enterButton.getFont());
                    FontMetrics fm = g.getFontMetrics();
                    String text = "ENTER STORE";
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getHeight();
                    int textX = buttonX + (buttonWidth - textWidth) / 2;
                    int textY = buttonY + (buttonHeight + textHeight) / 2 - fm.getDescent();
                    g.drawString(text, textX, textY);
                }
            }

            @Override
            public boolean mouseDown(Event evt, int x, int y) {
                // Check if click is within button bounds
                int buttonWidth = 200;
                int buttonHeight = 50;
                int buttonX = (getWidth() - buttonWidth) / 2;
                int buttonY = (getHeight() - buttonHeight) / 2;

                if (x >= buttonX && x <= buttonX + buttonWidth &&
                    y >= buttonY && y <= buttonY + buttonHeight) {
                    // Simulate button click
                    enterButton.dispatchEvent(new ActionEvent(enterButton, ActionEvent.ACTION_PERFORMED, "ENTER STORE"));
                    return true;
                }
                return false;
            }
        };

        // Make the panel focusable for mouse events
        imagePanel.setFocusable(true);
        add(imagePanel, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide landing page and show main GUI
                setVisible(false);
                mainGUI.setVisible(true);
                dispose(); // Close the landing page
            }
        });

        // Handle window closing
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void configureWindow() {
        setTitle("CityElectro - Welcome");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Ensure the image is redrawn when needed
        repaint();
    }
}
