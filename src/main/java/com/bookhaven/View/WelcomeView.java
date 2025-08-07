package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;

/**
 * The WelcomeView now directly EXTENDS ImagePanel, inheriting its ability
 * to draw a background image.
 */
public class WelcomeView extends ImagePanel {
    private String userName; // Changed from JPanel to ImagePanel

    public WelcomeView(String userName) {
        // 1. Call the parent's (ImagePanel's) constructor to set the background image.
        // The path MUST start with "/" to find the root of the resources folder.
        super("/welcomeViewBG.jpg");
        this.userName = userName;

        // 2. Set the layout for THIS panel to center the text.
        setLayout(new GridBagLayout());

        // 3. Create the welcome message label.
        String welcomeText = "<html>" +
                "<body style='width: 450px; text-align: center;'>" +
                "<h1>Welcome to BookHaven, "+userName+"!</h1><br>" +
                "<p>Your personal sanctuary for focused reading. In a world of digital noise, " +
                "BookHaven offers a clean and simple space to reconnect with the joy of books.</p><br>" +
                "<p>Use the navigation buttons above to explore your library.</p>" +
                "</body>" +
                "</html>";

        JLabel welcomeLabel = new JLabel(welcomeText);
        welcomeLabel.setForeground(Color.WHITE); // White text for visibility
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Optional: A semi-transparent panel behind the text for better readability.
        JPanel textPanel = new JPanel();
        textPanel.add(welcomeLabel);
        textPanel.setBackground(new Color(0, 0, 0, 150)); // Black with ~60% transparency
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 4. Add the textPanel directly to THIS panel (which is an ImagePanel).
        // Using GridBagLayout with default constraints perfectly centers the component.
        add(textPanel, new GridBagConstraints());
    }
}