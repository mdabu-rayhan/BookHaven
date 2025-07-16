package com.bookhaven.Utils;

import com.bookhaven.View.LoginView;
import com.bookhaven.View.RegistrationView;

import javax.swing.*;
import java.awt.*;

public class NavigationController {
    private final JPanel mainPanel;


    public NavigationController(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void showRegistrationView(){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Registration");
    }

    public void showLoginView(){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Login");
    }
}
