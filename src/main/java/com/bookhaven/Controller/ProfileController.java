package com.bookhaven.Controller;

import com.bookhaven.Service.UserService;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ProfileView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ProfileController {
    private final MainFrame mainFrame;
    private final ProfileView profileView;
    private final UserService userService;

    public ProfileController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.profileView = mainFrame.getProfileView();
        this.userService = new UserService();
        attachListeners();
    }

    private void attachListeners() {
        removeAllActionListeners(profileView.getChangePasswordButton());
        removeAllActionListeners(profileView.getLogoutButton());

        profileView.getChangePasswordButton().addActionListener(e -> handleChangePassword());
        profileView.getLogoutButton().addActionListener(e -> handleLogout());
    }

    private void removeAllActionListeners(AbstractButton button) {
        for (ActionListener l : button.getActionListeners()) {
            button.removeActionListener(l);
        }
    }

    public void loadProfile() {
        int userId = mainFrame.getUserId();
        var user = userService.getUserById(userId);
        if (user != null) {
            profileView.setFirstName(user.getFirstName());
            profileView.setLastName(user.getLastName());
            profileView.setEmail(user.getEmail());
            profileView.setUserId(user.getUserId());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Unable to load profile.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleChangePassword() {
        char[] oldPw = profileView.getOldPasswordField().getPassword();
        char[] newPw = profileView.getNewPasswordField().getPassword();
        char[] confirmPw = profileView.getConfirmPasswordField().getPassword();

        try {
            if (!Arrays.equals(newPw, confirmPw)) {
                JOptionPane.showMessageDialog(mainFrame, "New passwords do not match.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPw.length < 6) {
                JOptionPane.showMessageDialog(mainFrame, "Password must be at least 6 characters.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean updated = userService.changePassword(mainFrame.getUserId(), new String(oldPw), new String(newPw));
            if (updated) {
                JOptionPane.showMessageDialog(mainFrame, "Password updated successfully.");
                clearPasswordFields();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Old password incorrect or update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            // Wipe sensitive data
            Arrays.fill(oldPw, '\0');
            Arrays.fill(newPw, '\0');
            Arrays.fill(confirmPw, '\0');
        }
    }

    private void clearPasswordFields() {
        profileView.getOldPasswordField().setText("");
        profileView.getNewPasswordField().setText("");
        profileView.getConfirmPasswordField().setText("");
    }

    private void handleLogout() {
        // Dispose main frame and relaunch login window
        SwingUtilities.invokeLater(() -> {
            try {
                if (mainFrame != null) {
                    mainFrame.dispose();
                }
            } finally {
                // Relaunch the pre-login flow
                com.bookhaven.AppLauncher.main(new String[]{});
            }
        });
    }
}
