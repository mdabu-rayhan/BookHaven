package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends JPanel{
    private JButton registerButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;

    public RegistrationView(){
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel tittleLable = new JLabel("Create New User Account");
        tittleLable.setFont(new Font("SansSerif", Font.BOLD, 24));
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        grid.insets = new Insets(0, 0, 20, 0);
        grid.anchor = GridBagConstraints.CENTER;
        add(tittleLable, grid);


        JLabel firstNameLable = new JLabel("First Name:");
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        grid.insets = new Insets(6, 6, 6, 6);
        grid.anchor = GridBagConstraints.LINE_END;
        add(firstNameLable, grid);

        firstNameField = new JTextField(20);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.anchor = GridBagConstraints.LINE_START;
        add(firstNameField, grid);


        JLabel lastNameLable = new JLabel("Last Name:");
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        grid.insets = new Insets(6, 6, 6, 6);
        grid.anchor = GridBagConstraints.LINE_END;
        add(lastNameLable, grid);

        lastNameField = new JTextField(20);
        grid.gridx = 1;
        grid.gridy = 2;
        grid.anchor = GridBagConstraints.LINE_START;
        add(lastNameField, grid);


        JLabel emailLable = new JLabel("Email Address:");
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 1;
        grid.insets = new Insets(6, 6, 6, 6);
        grid.anchor = GridBagConstraints.LINE_END;
        add(emailLable, grid);


        emailField = new JTextField(20);
        grid.gridx = 1;
        grid.gridy = 3;
        grid.anchor = GridBagConstraints.LINE_START;
        add(emailField, grid);


        JLabel passwordLable = new JLabel("Password:");
        grid.gridx = 0;
        grid.gridy = 4;
        grid.anchor = GridBagConstraints.LINE_END;
        add(passwordLable, grid);

        passwordField = new JPasswordField(20);
        grid.gridx = 1;
        grid.gridy = 4;
        grid.anchor = GridBagConstraints.LINE_START;
        add(passwordField, grid);

        JLabel confirmPasswordLable = new JLabel("Password:");
        grid.gridx = 0;
        grid.gridy = 5;
        grid.anchor = GridBagConstraints.LINE_END;
        add(confirmPasswordLable, grid);

        confirmPasswordField = new JPasswordField(20);
        grid.gridx = 1;
        grid.gridy = 5;
        grid.anchor = GridBagConstraints.LINE_START;
        add(confirmPasswordField, grid);





    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BookHaven");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RegistrationView registrationView = new RegistrationView();

//        registrationView.addLoginListener(e -> {
//            String username = registrationView.obtainUsername();
//            char[] password = registrationView.obtainPassword();
//
//            JOptionPane.showConfirmDialog(frame,
//                    "Login Successful!\nUsername: " + username + "\nPassword: " + new String(password));
//        });

        frame.getContentPane().add(registrationView);
        frame.pack();
        frame.setSize(500, 600); // Set a larger frame size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
