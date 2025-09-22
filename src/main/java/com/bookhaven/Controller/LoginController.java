package com.bookhaven.Controller;

import com.bookhaven.Model.User;
import com.bookhaven.View.LoginView;
import com.bookhaven.Service.UserService;
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
        this.view.addLoginListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        String email = view.obtainUsername();
        char[] passwordChars = view.obtainPassword();
        String password = new String(passwordChars);

        try {
            User loginedUser = authservice.loginUser(email, password);
            if (loginedUser != null) {
                launchMainApplication(loginedUser);
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    private void launchMainApplication(User loggedInUser) {
        SwingUtilities.invokeLater(() -> {
            // close the login window so we don't keep two frames around
            java.awt.Window top = SwingUtilities.getWindowAncestor(view);
            if (top != null) {
                top.dispose();
            }
            MainFrame mainFrame = new MainFrame(loggedInUser);
            new MainFrameController(mainFrame); // wire navigation and views
            mainFrame.setVisible(true);
        });
    }
}
