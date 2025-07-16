package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class LoginView extends JPanel {
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel registerLink;

    public LoginView() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // tittle
        JLabel tittleLable = new JLabel("Book Reader Login");
        tittleLable.setFont(new Font("SansSerif", Font.BOLD, 24));
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        grid.insets = new Insets(0, 0, 20, 0);
        grid.anchor = GridBagConstraints.CENTER;
        add(tittleLable, grid);

        // username
        JLabel usernameLable = new JLabel("Username:");
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        grid.insets = new Insets(6, 6, 6, 6);
        grid.anchor = GridBagConstraints.LINE_END;
        add(usernameLable, grid);

        usernameField = new JTextField(20);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.anchor = GridBagConstraints.LINE_START;
        add(usernameField, grid);

        // password
        JLabel passwordLable = new JLabel("Password:");
        grid.gridx = 0;
        grid.gridy = 2;
        grid.anchor = GridBagConstraints.LINE_END;
        add(passwordLable, grid);

        passwordField = new JPasswordField(20);
        grid.gridx = 1;
        grid.gridy = 2;
        grid.anchor = GridBagConstraints.LINE_START;
        add(passwordField, grid);

        // Button
        loginButton = new JButton("Login");
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 2;
        grid.insets = new Insets(16, 0, 16, 0);
        grid.anchor = GridBagConstraints.CENTER;
        add(loginButton, grid);

        // Add some vertical space before registration panel
        grid.gridx = 0;
        grid.gridy = 5;
        grid.gridwidth = 2;
        grid.insets = new Insets(24, 0, 0, 0);
        grid.anchor = GridBagConstraints.CENTER;
        add(Box.createVerticalStrut(24), grid);

        // Registration panel at the bottom
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JLabel textLabel = new JLabel("Create your account -> ");
        registerLink = new JLabel("<html><a href=''>Register</a></html>");
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setForeground(new Color(0, 102, 204));
        registerLink.setFont(registerLink.getFont().deriveFont(Font.BOLD));
        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(LoginView.this, "Go to Register Page!");
            }
        });
        registerPanel.add(textLabel);
        registerPanel.add(registerLink);
        registerPanel.setOpaque(false);
        grid.gridx = 0;
        grid.gridy = 5;
        grid.gridwidth = 2;
        grid.insets = new Insets(24, 0, 0, 0);
        grid.anchor = GridBagConstraints.CENTER;
        add(registerPanel, grid);

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
        loginButton.addActionListener(listener);
    }

    public void addRegisterLinkListener(MouseAdapter listener) {
        registerLink.addMouseListener(listener);
    }

    // main method for test this plane independently
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("BookHaven");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        LoginView loginView = new LoginView();
//
//        // this segemnt should be in the controller file
//        loginView.addLoginListener(e -> {
//            String username = loginView.obtainUsername();
//            char[] password = loginView.obtainPassword();
//
//            JOptionPane.showConfirmDialog(frame,
//                    "Login Successful!\nUsername: " + username + "\nPassword: " + new String(password));
//        });
//
//        frame.getContentPane().add(loginView);
//        frame.pack();
//        frame.setSize(500, 600); // Set a larger frame size
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//    }
}
