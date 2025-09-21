package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPanel {
    // Profile info labels
    private final JLabel firstNameValue = new JLabel("-");
    private final JLabel lastNameValue = new JLabel("-");
    private final JLabel emailValue = new JLabel("-");
    private final JLabel userIdValue = new JLabel("-");

    // Password change controls
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
        gbc.gridx = 0; gbc.gridy = 0;

        // Header
        JLabel header = new JLabel("Your Profile");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 26f)); // increased
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(header);
        add(headerPanel, BorderLayout.NORTH);

        // Fonts for labels and values (larger and bold per request)
        Font labelFont = header.getFont().deriveFont(Font.BOLD, 16f); // increased
        Font valueFont = header.getFont().deriveFont(Font.BOLD, 18f); // increased
        Font sectionHeaderFont = header.getFont().deriveFont(Font.BOLD, 17f);
        Font buttonFont = header.getFont().deriveFont(Font.BOLD, 15f);

        // Apply fonts to value labels
        firstNameValue.setFont(valueFont);
        lastNameValue.setFont(valueFont);
        emailValue.setFont(valueFont);
        userIdValue.setFont(valueFont);

        // Ensure password fields and labels are comfortable to read
        oldPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        newPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        confirmPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Profile static fields
        content.add(new JLabel("First Name:"), gbc); gbc.gridx = 1; content.add(firstNameValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("Last Name:"), gbc); gbc.gridx = 1; content.add(lastNameValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("Email:"), gbc); gbc.gridx = 1; content.add(emailValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("User ID:"), gbc); gbc.gridx = 1; content.add(userIdValue, gbc);

        // Apply label font to the left-side static labels
        content.removeAll();
        gbc.gridx = 0; gbc.gridy = 0;
        content.add(new JLabel("First Name:"), gbc); gbc.gridx = 1; content.add(firstNameValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("Last Name:"), gbc); gbc.gridx = 1; content.add(lastNameValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("Email:"), gbc); gbc.gridx = 1; content.add(emailValue, gbc);
        gbc.gridx = 0; gbc.gridy++;
        content.add(new JLabel("User ID:"), gbc); gbc.gridx = 1; content.add(userIdValue, gbc);

        // Now apply the labelFont to all left-side labels we just added
        for (Component comp : content.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel lbl = (JLabel) comp;
                if (lbl.getText() != null && lbl.getText().endsWith(":")) {
                    lbl.setFont(labelFont);
                }
            }
        }

        // Spacer
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        content.add(Box.createVerticalStrut(12), gbc);
        gbc.gridwidth = 1;

        // Password section header
        gbc.gridx = 0; gbc.gridy++;
        JLabel pwHeader = new JLabel("Change Password");
        pwHeader.setFont(sectionHeaderFont);
        content.add(pwHeader, gbc);

        // Password fields
        gbc.gridx = 0; gbc.gridy++;
        JLabel oldLbl = new JLabel("Old Password:"); oldLbl.setFont(labelFont);
        content.add(oldLbl, gbc); gbc.gridx = 1; content.add(oldPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        JLabel newLbl = new JLabel("New Password:"); newLbl.setFont(labelFont);
        content.add(newLbl, gbc); gbc.gridx = 1; content.add(newPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        JLabel confLbl = new JLabel("Confirm New Password:"); confLbl.setFont(labelFont);
        content.add(confLbl, gbc); gbc.gridx = 1; content.add(confirmPasswordField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
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
    public void setFirstName(String firstName) { firstNameValue.setText(firstName != null ? firstName : "-"); }
    public void setLastName(String lastName) { lastNameValue.setText(lastName != null ? lastName : "-"); }
    public void setEmail(String email) { emailValue.setText(email != null ? email : "-"); }
    public void setUserId(int userId) { userIdValue.setText(String.valueOf(userId)); }

    // Getters for controller
    public JPasswordField getOldPasswordField() { return oldPasswordField; }
    public JPasswordField getNewPasswordField() { return newPasswordField; }
    public JPasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public JButton getChangePasswordButton() { return changePasswordButton; }
    public JButton getLogoutButton() { return logoutButton; }
}
