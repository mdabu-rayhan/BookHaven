package com.bookhaven.Controller;

import javax.swing.*;
import java.awt.*;

// PreLoginNavigationController: switches between Login and Registration views in the pre-login frame
public class PreLoginNavigationController {
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    public PreLoginNavigationController(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void showRegistrationView() {
        cardLayout.show(mainPanel, "Registration");
    }

    public void showLoginView() {
        cardLayout.show(mainPanel, "Login");
    }

}