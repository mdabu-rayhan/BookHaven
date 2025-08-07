package com.bookhaven.Controller;

import com.bookhaven.Model.User;
import com.bookhaven.View.LoginView;
import com.bookhaven.Service.UserService;
import com.bookhaven.Utils.PreLoginNavigationController;
import com.bookhaven.View.MainFrame;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;


public class LoginController {
    private LoginView view;
    private UserService authservice;
    private PreLoginNavigationController transition;

    public LoginController(LoginView view, UserService authservice, PreLoginNavigationController transition){
        this.view = view;
        this.transition = transition;
        this.authservice = authservice;

        // *** THIS IS THE CRITICAL FIX ***
        // Attach the handleLogin method to the button in the view.
        this.view.addLoginListener(this::handleLogin);

    }

    private void handleLogin(ActionEvent e) {
        String email = view.obtainUsername();
        char[] passwordChars = view.obtainPassword();
        String password = new String(passwordChars);  // convert char[] to String

        try {
            User loginedUser = authservice.loginUser(email, password);
            if (loginedUser != null) {
//
                launchMainApplication(loginedUser);
                 // Assume this navigates to the next screen
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // for debugging/logging
            JOptionPane.showMessageDialog(view, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Optionally clear password for security
            Arrays.fill(passwordChars, '\0');
        }
    }

    private void launchMainApplication(User loggedInUser) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create the View (The MainFrame)
            MainFrame mainFrame = new MainFrame(loggedInUser); // Assuming you updated constructor

            // 2. Create the Controller and GIVE it the view to control.
            // This single line wires up all the button actions for the MainFrame.
            MainFrameController mainFrameController = new MainFrameController(mainFrame);

            // 3. Make the fully constructed and controlled main frame visible.
            mainFrame.setVisible(true);
        });
    }


}
