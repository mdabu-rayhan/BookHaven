package com.bookhaven.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * This controller's SOLE RESPONSIBILITY is to manage navigation
 * in the PRE-LOGIN context (the initial frame).
 * It switches between the Login and Registration views.
 */
public class PreLoginNavigationController {
    private final JPanel mainPanel;
    private final CardLayout cardLayout;


    public PreLoginNavigationController(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void showRegistrationView() {
        // Switch the card in the pre-login panel to "Registration"
        cardLayout.show(mainPanel, "Registration");
    }

    public void showLoginView() {
        // Switch the card in the pre-login panel to "Registration"
        cardLayout.show(mainPanel, "Login");
    }



}