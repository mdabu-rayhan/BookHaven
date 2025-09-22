package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPanel {
    private final JLabel firstNameLabel = new JLabel("-");
    private final JLabel lastNameLabel = new JLabel("-");
    private final JLabel emailLabel = new JLabel("-");
    private final JLabel userIdLabel = new JLabel("-");


    private final JPasswordField oldPasswordField = new JPasswordField(16);
    private final JPasswordField newPasswordField = new JPasswordField(16);
    private final JPasswordField confirmPasswordField = new JPasswordField(16);
    private final JButton changePasswordButton = new JButton("Change Password");

    // Logout
    private final JButton logoutButton = new JButton("Logout");

    public ProfileView() {
        setLayout(new BorderLayout());

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;


        JLabel header = new JLabel("Your Profile");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 26f)); // increased
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(header);
        add(headerPanel, BorderLayout.NORTH);

        // font size increase
        Font labelFont = header.getFont().deriveFont(Font.BOLD, 16f);
        Font valueFont = header.getFont().deriveFont(Font.BOLD, 18f);
        Font sectionHeaderFont = header.getFont().deriveFont(Font.BOLD, 17f);
        Font buttonFont = header.getFont().deriveFont(Font.BOLD, 15f);

        // Apply fonts to value labels
        firstNameLabel.setFont(valueFont);
        lastNameLabel.setFont(valueFont);
        emailLabel.setFont(valueFont);
        userIdLabel.setFont(valueFont);

        // Ensure password fields and labels are comfortable to read
        oldPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        newPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        confirmPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Profile static fields
        JLabel firstNameStatic = new JLabel("First Name:");
        JLabel lastNameStatic  = new JLabel("Last Name:");
        JLabel emailStatic     = new JLabel("Email:");
        JLabel userIdStatic    = new JLabel("User ID:");

        firstNameStatic.setFont(labelFont);
        lastNameStatic.setFont(labelFont);
        emailStatic.setFont(labelFont);
        userIdStatic.setFont(labelFont);

        // first name
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(firstNameStatic, gbc);
        gbc.gridx = 1;
        content.add(firstNameLabel, gbc);

        //last anem
        gbc.gridx = 0;
        gbc.gridy++;
        content.add(lastNameStatic, gbc);
        gbc.gridx = 1;
        content.add(lastNameLabel, gbc);

        //email
        gbc.gridx = 0;
        gbc.gridy++;
        content.add(emailStatic, gbc);
        gbc.gridx = 1;
        content.add(emailLabel, gbc);


        //user Id
        gbc.gridx = 0;
        gbc.gridy++;
        content.add(userIdStatic, gbc);
        gbc.gridx = 1;
        content.add(userIdLabel, gbc);



        // Space
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        content.add(Box.createVerticalStrut(12), gbc);
        gbc.gridwidth = 1;

        // Password section header
        gbc.gridx = 0; gbc.gridy++;
        JLabel pwHeader = new JLabel("Change Password");
        pwHeader.setFont(sectionHeaderFont);
        content.add(pwHeader, gbc);

        // Password fields
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel oldLbl = new JLabel("Old Password:");
        oldLbl.setFont(labelFont);
        content.add(oldLbl, gbc);
        gbc.gridx = 1;
        content.add(oldPasswordField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        JLabel newLbl = new JLabel("New Password:");
        newLbl.setFont(labelFont);
        content.add(newLbl, gbc);
        gbc.gridx = 1;
        content.add(newPasswordField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        JLabel confLbl = new JLabel("Confirm New Password:");
        confLbl.setFont(labelFont);
        content.add(confLbl, gbc);
        gbc.gridx = 1;
        content.add(confirmPasswordField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        changePasswordButton.setFont(buttonFont);

        logoutButton.setFont(buttonFont);
        buttons.add(changePasswordButton);

        buttons.add(Box.createHorizontalStrut(12));
        buttons.add(logoutButton);
        content.add(buttons, gbc);

        add(content, BorderLayout.CENTER);
    }

    // Setters for profile text
    public void setFirstName(String firstName) {
        firstNameLabel.setText(firstName != null ? firstName : "-");
    }
    public void setLastName(String lastName) {
        lastNameLabel.setText(lastName != null ? lastName : "-");
    }

    public void setEmail(String email) {
        emailLabel.setText(email != null ? email : "-");
    }

    public void setUserId(int userId) {

        userIdLabel.setText(String.valueOf(userId));
    }

    // Getters for controller
    public JPasswordField getOldPasswordField() {
        return oldPasswordField;
    }
    public JPasswordField getNewPasswordField() {
        return newPasswordField;
    }
    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }
    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
    public JButton getLogoutButton() {
        return logoutButton;
    }
}
