package com.bookhaven;

import com.bookhaven.Controller.LoginController;
import com.bookhaven.Controller.RegistrationController;
import com.bookhaven.Service.UserService;
import com.bookhaven.Utils.PreLoginNavigationController;
import com.bookhaven.View.LoginView;
import com.bookhaven.View.RegistrationView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppLauncher {
    public static void main(String[] args) {
        // It's best practice to run Swing applications on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {


            JFrame mainFrame = new JFrame("BookHaven");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel mainPanel = new JPanel();
            CardLayout cardLayout = new CardLayout();
            mainPanel.setLayout(cardLayout);


            LoginView loginView = new LoginView();
            RegistrationView registrationView = new RegistrationView();


            mainPanel.add(loginView, "Login");
            mainPanel.add(registrationView, "Registration");


            UserService userService = new UserService();
            PreLoginNavigationController preLoginNavigationController = new PreLoginNavigationController(mainPanel, cardLayout);


            LoginController loginController = new LoginController(loginView, userService, preLoginNavigationController);
            RegistrationController registrationController = new RegistrationController(registrationView, userService, preLoginNavigationController);


            loginView.addRegisterLinkListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    preLoginNavigationController.showRegistrationView();
                }
            });

            mainFrame.getContentPane().add(mainPanel);
            mainFrame.pack();
            mainFrame.setSize(500, 600);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}