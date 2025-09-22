package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;


public class LoginView extends JPanel {


    // These are now instance variables so they can be accessed by all methods in this class.
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel registerLink;


    public LoginView() {
        initComponents(); // Create and style all the components
        initLayout();   // Arrange all the components on the panel
    }


    private void initComponents() {
        //buttons
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // text field
        usernameField = new JTextField(20); // 20 columns wide
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // labels
        registerLink = new JLabel("<html><a href=''>Register</a></html>");
        registerLink.setFont(new Font("SansSerif", Font.BOLD, 12));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    private void initLayout() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Padding around the whole panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Consistent spacing between components
        gbc.anchor = GridBagConstraints.CENTER; // Make components fill their horizontal space


        JLabel titleLabel = new JLabel("BookHaven Login");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        add(titleLabel, gbc);


        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to one column
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span two columns
        gbc.fill = GridBagConstraints.NONE; // Don't stretch the button
        gbc.anchor = GridBagConstraints.CENTER; // Center it
        add(loginButton, gbc);


        // Create a small panel to hold the "Create account" text and the link together
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        registerPanel.add(new JLabel("Don't have an account?"));
        registerPanel.add(registerLink);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 0, 0); // Add some top margin
        add(registerPanel, gbc);
    }



    public String obtainUsername() {
        return usernameField.getText();
    }

    public char[] obtainPassword() {
        return passwordField.getPassword();
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }


    public void addLoginListener(ActionListener listener) {
        for (ActionListener old : loginButton.getActionListeners()) {
            loginButton.removeActionListener(old);
        }
        loginButton.addActionListener(listener);
    }


    public void addRegisterLinkListener(MouseAdapter listener) {
        // It's good practice to remove old listeners before adding a new one
        for (MouseListener oldListener : registerLink.getMouseListeners()) {
            registerLink.removeMouseListener(oldListener);
        }
        registerLink.addMouseListener(listener);
    }
}