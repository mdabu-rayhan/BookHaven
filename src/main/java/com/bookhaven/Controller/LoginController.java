package com.bookhaven.Controller;

import com.bookhaven.View.LoginView;
import com.bookhaven.Service.UserService;
import com.bookhaven.Utils.NavigationController;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;


public class LoginController {
    private LoginView view;
    private UserService authservice;
    private NavigationController transition;

    public LoginController(LoginView view, UserService authservice, NavigationController transition){
        this.view = view;
        this.transition = transition;
        this.authservice = authservice;

        // *** THIS IS THE CRITICAL FIX ***
        // Attach the handleLogin method to the button in the view.
        this.view.addLoginListener(this::handleLogin);

    }

    private void handleLogin(ActionEvent e) {
        String username = view.obtainUsername();
        char[] passwordChars = view.obtainPassword();
        String password = new String(passwordChars);  // convert char[] to String

        try {
            boolean success = authservice.loginUser(username, password);
            if (success) {
                JOptionPane.showMessageDialog(view, "Login successful!");
                //transition.navigateToDashboard(); // Assume this navigates to the next screen
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
}
