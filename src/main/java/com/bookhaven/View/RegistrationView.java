
package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JPanel {
    private JButton registerButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;

    public RegistrationView() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid.insets = new Insets(10, 5, 10, 5);  // Increased vertical spacing

        // Create larger fonts
        Font labelFont = new Font("SansSerif", Font.BOLD, 18);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        Font headerFont = new Font("Arial", Font.BOLD | Font.ITALIC, 32);


        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        grid.anchor = GridBagConstraints.CENTER;
        grid.insets = new Insets(0, 0, 30, 0);  // Bottom margin

        JLabel header = new JLabel("Registration Form");
        header.setFont(headerFont);
        add(header, grid);


        grid.gridwidth = 1;
        grid.insets = new Insets(5, 5, 5, 5);
        grid.anchor = GridBagConstraints.LINE_START;

        // ===== 2. FIRST NAME =====
        grid.gridy = 1;
        add(createLabel("First Name:", labelFont), grid);

        grid.gridy = 2;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        firstNameField = createTextField(fieldFont);
        add(firstNameField, grid);

        // ===== 3. LAST NAME =====
        grid.gridy = 3;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        add(createLabel("Last Name:", labelFont), grid);

        grid.gridy = 4;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        lastNameField = createTextField(fieldFont);
        add(lastNameField, grid);


        grid.gridy = 5;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        add(createLabel("Email Address:", labelFont), grid);

        grid.gridy = 6;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        emailField = createTextField(fieldFont);
        add(emailField, grid);

        // ===== 5. PASSWORD =====
        grid.gridy = 7;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        add(createLabel("Password:", labelFont), grid);

        grid.gridy = 8;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        passwordField = createPasswordField(fieldFont);
        add(passwordField, grid);


        grid.gridy = 9;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        add(createLabel("Confirm Password:", labelFont), grid);

        grid.gridy = 10;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        confirmPasswordField = createPasswordField(fieldFont);
        add(confirmPasswordField, grid);


        grid.gridy = 11;
        grid.gridx = 0;
        grid.gridwidth = 2;
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        grid.anchor = GridBagConstraints.CENTER;
        grid.insets = new Insets(30, 0, 0, 0);  // Top margin

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(150, 40));
        add(registerButton, grid);
    }


    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField field = new JTextField(20);
        field.setFont(font);
        field.setPreferredSize(new Dimension(250, 35));
        return field;
    }

    private JPasswordField createPasswordField(Font font) {
        JPasswordField field = new JPasswordField(20);
        field.setFont(font);
        field.setPreferredSize(new Dimension(250, 35));
        return field;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BookHaven");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setPreferredSize(new Dimension(500, 700));

        frame.getContentPane().add(new RegistrationView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}