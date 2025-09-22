package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;


public class RegistrationView extends JPanel {

    // --- Fields for UI Components ---
    private JButton registerButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel backToLoginLink;

    public RegistrationView() {
        initComponents();
        initLayout();
    }


    private void initComponents() {

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);


        firstNameField = new JTextField();
        firstNameField.setFont(fieldFont);
        firstNameField.setPreferredSize(new Dimension(200, 25));
        firstNameField.setMinimumSize(new Dimension(200, 25));

        lastNameField = new JTextField();
        lastNameField.setFont(fieldFont);
        lastNameField.setPreferredSize(new Dimension(200, 25));
        lastNameField.setMinimumSize(new Dimension(200, 25));

        emailField = new JTextField();
        emailField.setFont(fieldFont);
        emailField.setPreferredSize(new Dimension(200, 25));
        emailField.setMinimumSize(new Dimension(200, 25));

        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(200, 25));
        passwordField.setMinimumSize(new Dimension(200, 25));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(fieldFont);
        confirmPasswordField.setPreferredSize(new Dimension(200, 25));
        confirmPasswordField.setMinimumSize(new Dimension(200, 25));


        registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setPreferredSize(new Dimension(140, 35));


        backToLoginLink = new JLabel("Back to Login");
        backToLoginLink.setFont(new Font("SansSerif", Font.PLAIN, 12));
        backToLoginLink.setForeground(Color.BLUE);
        backToLoginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    private void initLayout() {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);


        JLabel header = new JLabel("Create Your Account");
        header.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(header, gbc);

        // reset insets for form fields
        gbc.insets = new Insets(8, 8, 8, 8);


        int currentY = 1; // Start at the next row

        // First Name
        gbc.gridy = currentY;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START; // Left align labels
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE; // Don't stretch horizontally
        gbc.weightx = 0;
        add(firstNameField, gbc);
        currentY++;

        // Last Name
        gbc.gridy = currentY;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Left align labels
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(lastNameField, gbc);
        currentY++;

        // Email Address
        gbc.gridy = currentY;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Left align labels
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Email Address:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(emailField, gbc);
        currentY++;

        // Password
        gbc.gridy = currentY;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Left align labels
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(passwordField, gbc);
        currentY++;

        // Confirm Password
        gbc.gridy = currentY;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Left align labels
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(confirmPasswordField, gbc);
        currentY++;

        // reg button
        gbc.gridx = 0;
        gbc.gridy = currentY++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // Don't stretch the button
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        add(registerButton, gbc);

        //
        gbc.gridy = currentY;
        gbc.insets = new Insets(15, 0, 0, 0);
        add(backToLoginLink, gbc);
    }




    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getEmailField() {
        return emailField.getText().trim();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPasswordField() {
        return confirmPasswordField.getPassword();
    }

    public void clearPasswordFields() {
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        clearPasswordFields();
    }

    // java
    public void addRegisterListener(ActionListener listener) {
        for (ActionListener old : registerButton.getActionListeners()) {
            registerButton.removeActionListener(old);
        }
        registerButton.addActionListener(listener);
    }

    public void addBackToLoginListener(MouseAdapter listener) {
        // Remove old listeners before adding a new one to prevent duplicates
        for (MouseListener oldListener : backToLoginLink.getMouseListeners()) {
            backToLoginLink.removeMouseListener(oldListener);
        }
        backToLoginLink.addMouseListener(listener);
    }
}