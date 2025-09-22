
package com.bookhaven.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class WelcomeView extends JPanel {
    private final String userName;
    private Image backgroundImage;

    public WelcomeView(String userName) {
        this.userName = userName;
        loadBackground("/welcomeViewBG.jpg");
        setLayout(new GridBagLayout());
        initContent();
    }

    private void loadBackground(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                backgroundImage = ImageIO.read(is);
            } else {
                backgroundImage = null;
                setBackground(Color.DARK_GRAY);
                System.err.println("Background resource not found: " + path);
            }
        } catch (IOException e) {
            backgroundImage = null;
            setBackground(Color.DARK_GRAY);
            e.printStackTrace();
        }
    }

    private void initContent() {
        String welcomeText = "<html>" +
                "<body style='width: 450px; text-align: center;'>" +
                "<h1>Welcome to BookHaven, " + userName + "!</h1><br>" +
                "<p>Your personal sanctuary for focused reading. In a world of digital noise, " +
                "BookHaven offers a clean and simple space to reconnect with the joy of books.</p><br>" +
                "<p>Use the navigation buttons above to explore your library.</p>" +
                "</body>" +
                "</html>";

        JLabel welcomeLabel = new JLabel(welcomeText);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.add(welcomeLabel);
        textPanel.setBackground(new Color(0, 0, 0, 150)); // semi-transparent
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(textPanel, new GridBagConstraints());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}